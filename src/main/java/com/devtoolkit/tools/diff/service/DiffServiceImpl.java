package com.devtoolkit.tools.diff.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.diff.constants.DiffConstants;
import com.devtoolkit.tools.diff.dto.DiffRequest;
import com.devtoolkit.tools.diff.dto.DiffResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for Diff operations
 * 
 * <p>This service provides comprehensive text comparison capabilities including:
 * - Basic text comparison
 * - Enhanced diff generation with multiple formats (TEXT, JSON, XML, CODE)
 * - Character-level and line-level analysis
 * - Side-by-side comparison with highlighting
 * - Statistical analysis and similarity metrics</p>
 * 
 * <p><strong>Note:</strong> This service assumes that all input validation has been performed
 * at the controller level. The service focuses purely on business logic.</p>
 * 
 * @author DevToolkit
 * @version 1.0
 */
@Slf4j
@Service
public class DiffServiceImpl implements DiffService {
    
    private final ObjectMapper objectMapper;
    private final LevenshteinDistance levenshteinDistance;
    
    public DiffServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.levenshteinDistance = new LevenshteinDistance();
    }
    
    /**
     * Performs basic text comparison between two strings
     * 
     * <p><strong>Precondition:</strong> Input validation should be performed at the controller level.
     * This method assumes that the input parameters are valid.</p>
     * 
     * @param text1 the first text to compare (assumed to be validated)
     * @param text2 the second text to compare (assumed to be validated)
     * @return DiffResponse containing the comparison results
     * @throws DevToolkitException if comparison fails
     */
    @Override
    public DiffResponse compareText(String text1, String text2) {
        log.debug("Starting basic text comparison");
        
        try {
            // Normalize inputs
            String normalizedText1 = normalizeText(text1);
            String normalizedText2 = normalizeText(text2);
            
            // Perform comparison
            boolean identical = normalizedText1.equals(normalizedText2);
            List<String> differences = buildBasicDifferences(identical);
            
            // Build response
            DiffResponse response = buildBasicResponse(normalizedText1, normalizedText2, differences, identical);
            
            log.info("Basic text comparison completed. Identical: {}", identical);
            return response;
            
        } catch (Exception e) {
            log.error("Error during basic text comparison: {}", e.getMessage(), e);
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, "Error comparing texts");
        }
    }
    
    /**
     * Generates enhanced diff analysis with multiple comparison types
     * 
     * <p><strong>Precondition:</strong> Input validation should be performed at the controller level.
     * This method assumes that the input parameters are valid.</p>
     * 
     * @param request the diff request containing texts and options (assumed to be validated)
     * @return DiffResponse containing enhanced diff analysis
     * @throws DevToolkitException if diff generation fails
     */
    @Override
    public DiffResponse generateEnhancedDiff(DiffRequest request) {
        log.info("Starting enhanced diff generation for type: {}", request.getDiffType());
        
        try {
            // Preprocess texts based on request options
            String processedText1 = preprocessText(request.getText1(), request);
            String processedText2 = preprocessText(request.getText2(), request);
            
            // Check if texts are identical after preprocessing
            boolean identical = processedText1.equals(processedText2);
            
            // Build base response
            DiffResponse response = buildEnhancedResponse(request, identical);
            
            if (identical) {
                log.info("Texts are identical after preprocessing");
                return response;
            }
            
            // Generate enhanced diff based on type
            Map<String, Object> enhancedDiff = generateDiffByType(processedText1, processedText2, request);
            response.setEnhancedDiff(enhancedDiff);
            
            log.info("Enhanced diff generation completed successfully for type: {}", request.getDiffType());
            return response;
            
        } catch (DevToolkitException e) {
            log.error("Enhanced diff generation failed with DevToolkitException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during enhanced diff generation: {}", e.getMessage(), e);
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, "Error generating enhanced diff");
        }
    }
    
    /**
     * Normalizes text input for comparison
     * 
     * @param text the text to normalize
     * @return the normalized text
     */
    private String normalizeText(String text) {
        return text != null ? text : "";
    }
    
    /**
     * Builds basic differences list based on comparison result
     * 
     * @param identical whether the texts are identical
     * @return list of difference descriptions
     */
    private List<String> buildBasicDifferences(boolean identical) {
        List<String> differences = new ArrayList<>();
        if (identical) {
            differences.add("No differences found");
        } else {
            differences.add("Found differences");
        }
        return differences;
    }
    
    /**
     * Builds basic response for text comparison
     * 
     * @param text1 the first text
     * @param text2 the second text
     * @param differences the list of differences
     * @param identical whether texts are identical
     * @return the DiffResponse
     */
    private DiffResponse buildBasicResponse(String text1, String text2, List<String> differences, boolean identical) {
        DiffResponse response = new DiffResponse();
        response.setText1(text1);
        response.setText2(text2);
        response.setDifferences(differences);
        response.setHasDifferences(!identical);
        response.setMessage(DiffConstants.COMPARE_SUCCESS_MESSAGE);
        return response;
    }
    
    /**
     * Preprocesses text based on diff options
     * 
     * @param text the text to preprocess
     * @param request the diff request containing preprocessing options
     * @return the preprocessed text
     */
    private String preprocessText(String text, DiffRequest request) {
        if (text == null) {
            return "";
        }
        
        String processed = text;
        
        // Normalize line endings
        if (request.isIgnoreLineEndings()) {
            processed = processed.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        }
        
        // Normalize whitespace
        if (request.isIgnoreWhitespace()) {
            processed = processed.replaceAll("\\s+", " ").trim();
        }
        
        // Normalize case
        if (request.isIgnoreCase()) {
            processed = processed.toLowerCase();
        }
        
        return processed;
    }
    
    /**
     * Builds enhanced response with basic information
     * 
     * @param request the original request
     * @param identical whether texts are identical
     * @return the DiffResponse
     */
    private DiffResponse buildEnhancedResponse(DiffRequest request, boolean identical) {
        DiffResponse response = new DiffResponse();
        response.setText1(request.getText1());
        response.setText2(request.getText2());
        response.setHasDifferences(!identical);
        response.setMessage(DiffConstants.ENHANCED_COMPARE_SUCCESS_MESSAGE);
        
        if (identical) {
            response.setDifferences(new ArrayList<>());
            response.setEnhancedDiff(new HashMap<>());
        }
        
        return response;
    }
    
    /**
     * Generates diff based on the specified type
     * 
     * @param text1 the first processed text
     * @param text2 the second processed text
     * @param request the diff request
     * @return the enhanced diff map
     */
    private Map<String, Object> generateDiffByType(String text1, String text2, DiffRequest request) {
        switch (request.getDiffType()) {
            case JSON:
                return generateJsonDiff(text1, text2);
            case XML:
                return generateXmlDiff(text1, text2);
            case CODE:
                return generateCodeDiff(text1, text2, request);
            default:
                return generateTextDiff(text1, text2, request);
        }
    }
    
    /**
     * Generates comprehensive text diff with multiple analysis types
     * 
     * @param text1 the first text
     * @param text2 the second text
     * @param request the diff request
     * @return the enhanced diff map
     */
    private Map<String, Object> generateTextDiff(String text1, String text2, DiffRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // Split texts into lines
        List<String> lines1 = Arrays.asList(text1.split("\n", -1));
        List<String> lines2 = Arrays.asList(text2.split("\n", -1));
        
        // Generate unified diff with context
        String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, request.getContextLines());
        result.put(DiffConstants.KEY_UNIFIED_DIFF, unifiedDiff);
        
        // Generate side-by-side comparison
        Map<String, Object> sideBySide = generateEnhancedSideBySideDiff(lines1, lines2);
        result.put(DiffConstants.KEY_SIDE_BY_SIDE, sideBySide);
        
        // Generate character-level diff
        List<Map<String, Object>> diffDetails = generateCharacterLevelDiff(text1, text2);
        result.put(DiffConstants.KEY_DIFF_DETAILS, diffDetails);
        
        // Calculate comprehensive statistics
        Map<String, Object> stats = calculateEnhancedStatistics(lines1, lines2, diffDetails);
        result.put(DiffConstants.KEY_STATISTICS, stats);
        
        return result;
    }
    
    /**
     * Generates context-aware unified diff format
     * 
     * @param lines1 the first set of lines
     * @param lines2 the second set of lines
     * @param contextLines the number of context lines to include
     * @return the unified diff string
     */
    private String generateContextAwareUnifiedDiff(List<String> lines1, List<String> lines2, int contextLines) {
        List<Integer> changedLines = findChangedLines(lines1, lines2);
        
        if (changedLines.isEmpty()) {
            return "";
        }
        
        List<List<Integer>> changeGroups = groupConsecutiveChanges(changedLines, contextLines);
        return buildUnifiedDiff(lines1, lines2, changeGroups, contextLines);
    }
    
    /**
     * Finds lines that have changed between two text sets
     * 
     * @param lines1 the first set of lines
     * @param lines2 the second set of lines
     * @return list of line indices that have changed
     */
    private List<Integer> findChangedLines(List<String> lines1, List<String> lines2) {
        List<Integer> changedLines = new ArrayList<>();
        int maxLines = Math.max(lines1.size(), lines2.size());
        
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < lines1.size() ? lines1.get(i) : "";
            String line2 = i < lines2.size() ? lines2.get(i) : "";
            if (!line1.equals(line2)) {
                changedLines.add(i);
            }
        }
        
        return changedLines;
    }
    
    /**
     * Groups consecutive changes for context-aware diff
     * 
     * @param changedLines the list of changed line indices
     * @param contextLines the number of context lines
     * @return grouped changes
     */
    private List<List<Integer>> groupConsecutiveChanges(List<Integer> changedLines, int contextLines) {
        List<List<Integer>> groups = new ArrayList<>();
        List<Integer> currentGroup = new ArrayList<>();
        
        for (int i = 0; i < changedLines.size(); i++) {
            if (currentGroup.isEmpty()) {
                currentGroup.add(changedLines.get(i));
            } else {
                int lastInGroup = currentGroup.get(currentGroup.size() - 1);
                if (changedLines.get(i) - lastInGroup <= contextLines * 2) {
                    currentGroup.add(changedLines.get(i));
                } else {
                    groups.add(new ArrayList<>(currentGroup));
                    currentGroup.clear();
                    currentGroup.add(changedLines.get(i));
                }
            }
        }
        
        if (!currentGroup.isEmpty()) {
            groups.add(currentGroup);
        }
        
        return groups;
    }
    
    /**
     * Builds unified diff string from grouped changes
     * 
     * @param lines1 the first set of lines
     * @param lines2 the second set of lines
     * @param changeGroups the grouped changes
     * @param contextLines the number of context lines
     * @return the unified diff string
     */
    private String buildUnifiedDiff(List<String> lines1, List<String> lines2, 
                                   List<List<Integer>> changeGroups, int contextLines) {
        StringBuilder diff = new StringBuilder();
        int maxLines = Math.max(lines1.size(), lines2.size());
        
        for (List<Integer> group : changeGroups) {
            int start = Math.max(0, group.get(0) - contextLines);
            int end = Math.min(maxLines, group.get(group.size() - 1) + contextLines + 1);
            
            diff.append(String.format("@@ -%d,%d +%d,%d @@\n", 
                start + 1, end - start, start + 1, end - start));
            
            for (int i = start; i < end; i++) {
                String line1 = i < lines1.size() ? lines1.get(i) : "";
                String line2 = i < lines2.size() ? lines2.get(i) : "";
                
                if (line1.equals(line2)) {
                    diff.append("  ").append(line1).append("\n");
                } else {
                    if (i < lines1.size()) {
                        diff.append("- ").append(line1).append("\n");
                    }
                    if (i < lines2.size()) {
                        diff.append("+ ").append(line2).append("\n");
                    }
                }
            }
            diff.append("\n");
        }
        
        return diff.toString();
    }
    
    /**
     * Generates enhanced side-by-side diff with highlighting
     * 
     * @param lines1 the first set of lines
     * @param lines2 the second set of lines
     * @return the side-by-side comparison map
     */
    private Map<String, Object> generateEnhancedSideBySideDiff(List<String> lines1, List<String> lines2) {
        Map<String, Object> sideBySide = new HashMap<>();
        List<Map<String, Object>> comparison = new ArrayList<>();
        
        int maxLines = Math.max(lines1.size(), lines2.size());
        
        for (int i = 0; i < maxLines; i++) {
            Map<String, Object> lineComparison = buildLineComparison(lines1, lines2, i);
            comparison.add(lineComparison);
        }
        
        sideBySide.put(DiffConstants.KEY_COMPARISON, comparison);
        sideBySide.put(DiffConstants.KEY_TOTAL_LINES, maxLines);
        
        return sideBySide;
    }
    
    /**
     * Builds comparison for a single line
     * 
     * @param lines1 the first set of lines
     * @param lines2 the second set of lines
     * @param lineIndex the line index to compare
     * @return the line comparison map
     */
    private Map<String, Object> buildLineComparison(List<String> lines1, List<String> lines2, int lineIndex) {
        Map<String, Object> lineComparison = new HashMap<>();
        String line1 = lineIndex < lines1.size() ? lines1.get(lineIndex) : "";
        String line2 = lineIndex < lines2.size() ? lines2.get(lineIndex) : "";
        
        lineComparison.put(DiffConstants.KEY_LINE_NUMBER, lineIndex + 1);
        lineComparison.put(DiffConstants.KEY_LEFT, line1);
        lineComparison.put(DiffConstants.KEY_RIGHT, line2);
        
        if (line1.equals(line2)) {
            lineComparison.put(DiffConstants.KEY_STATUS, DiffConstants.STATUS_UNCHANGED);
            lineComparison.put(DiffConstants.KEY_CSS_CLASS, DiffConstants.CSS_CLASS_UNCHANGED);
        } else {
            lineComparison.put(DiffConstants.KEY_STATUS, DiffConstants.STATUS_MODIFIED);
            lineComparison.put(DiffConstants.KEY_CSS_CLASS, DiffConstants.CSS_CLASS_MODIFIED);
            
            // Add character-level highlighting
            List<Map<String, Object>> leftHighlights = generateCharacterHighlights(line1, line2);
            List<Map<String, Object>> rightHighlights = generateCharacterHighlights(line2, line1);
            
            lineComparison.put(DiffConstants.KEY_LEFT_HIGHLIGHTS, leftHighlights);
            lineComparison.put(DiffConstants.KEY_RIGHT_HIGHLIGHTS, rightHighlights);
        }
        
        return lineComparison;
    }
    
    /**
     * Generates character-level highlights for side-by-side comparison
     * 
     * @param text1 the first text
     * @param text2 the second text
     * @return list of highlight maps
     */
    private List<Map<String, Object>> generateCharacterHighlights(String text1, String text2) {
        List<Map<String, Object>> highlights = new ArrayList<>();
        
        // Calculate similarity using Levenshtein distance
        int distance = levenshteinDistance.apply(text1, text2);
        double similarity = 1.0 - (double) distance / Math.max(text1.length(), text2.length());
        
        if (similarity > 0.5) {
            // Find common parts
            int commonPrefix = findCommonPrefix(text1, text2);
            int commonSuffix = findCommonSuffix(text1, text2);
            
            // Add prefix highlight
            if (commonPrefix > 0) {
                highlights.add(createHighlight(0, commonPrefix, DiffConstants.STATUS_UNCHANGED, DiffConstants.CSS_CLASS_UNCHANGED));
            }
            
            // Add middle highlight
            if (commonPrefix < text1.length() - commonSuffix) {
                highlights.add(createHighlight(commonPrefix, text1.length() - commonSuffix, DiffConstants.STATUS_CHANGED, DiffConstants.CSS_CLASS_CHANGED));
            }
            
            // Add suffix highlight
            if (commonSuffix > 0) {
                highlights.add(createHighlight(text1.length() - commonSuffix, text1.length(), DiffConstants.STATUS_UNCHANGED, DiffConstants.CSS_CLASS_UNCHANGED));
            }
        } else {
            // Completely different
            highlights.add(createHighlight(0, text1.length(), DiffConstants.STATUS_DELETED, DiffConstants.CSS_CLASS_DELETED));
        }
        
        return highlights;
    }
    
    /**
     * Creates a highlight map
     * 
     * @param start the start position
     * @param end the end position
     * @param type the highlight type
     * @param cssClass the CSS class
     * @return the highlight map
     */
    private Map<String, Object> createHighlight(int start, int end, String type, String cssClass) {
        Map<String, Object> highlight = new HashMap<>();
        highlight.put(DiffConstants.KEY_START, start);
        highlight.put(DiffConstants.KEY_END, end);
        highlight.put(DiffConstants.KEY_TYPE, type);
        highlight.put(DiffConstants.KEY_CSS_CLASS, cssClass);
        return highlight;
    }
    
    /**
     * Finds the common prefix length between two strings
     * 
     * @param s1 the first string
     * @param s2 the second string
     * @return the length of the common prefix
     */
    private int findCommonPrefix(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());
        for (int i = 0; i < minLength; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return i;
            }
        }
        return minLength;
    }
    
    /**
     * Finds the common suffix length between two strings
     * 
     * @param s1 the first string
     * @param s2 the second string
     * @return the length of the common suffix
     */
    private int findCommonSuffix(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());
        for (int i = 1; i <= minLength; i++) {
            if (s1.charAt(s1.length() - i) != s2.charAt(s2.length() - i)) {
                return i - 1;
            }
        }
        return minLength;
    }
    
    /**
     * Generates character-level diff with advanced algorithms
     * 
     * @param text1 the first text
     * @param text2 the second text
     * @return list of diff details
     */
    private List<Map<String, Object>> generateCharacterLevelDiff(String text1, String text2) {
        List<Map<String, Object>> diffDetails = new ArrayList<>();
        
        // Calculate similarity
        int distance = levenshteinDistance.apply(text1, text2);
        double similarity = 1.0 - (double) distance / Math.max(text1.length(), text2.length());
        
        if (similarity > 0.8) {
            // High similarity - find common parts
            buildDetailedCharacterDiff(text1, text2, diffDetails);
        } else {
            // Low similarity - treat as completely different
            buildCompleteCharacterDiff(text1, text2, diffDetails);
        }
        
        return diffDetails;
    }
    
    /**
     * Builds detailed character diff for similar texts
     * 
     * @param text1 the first text
     * @param text2 the second text
     * @param diffDetails the list to add diff details to
     */
    private void buildDetailedCharacterDiff(String text1, String text2, List<Map<String, Object>> diffDetails) {
        int commonPrefix = findCommonPrefix(text1, text2);
        int commonSuffix = findCommonSuffix(text1, text2);
        
        // Add prefix
        if (commonPrefix > 0) {
            diffDetails.add(createDiffDetail(DiffConstants.OPERATION_EQUAL, text1.substring(0, commonPrefix), commonPrefix, DiffConstants.STATUS_UNCHANGED, DiffConstants.CSS_CLASS_UNCHANGED));
        }
        
        // Add middle differences
        if (commonPrefix < text1.length() - commonSuffix) {
            diffDetails.add(createDiffDetail(DiffConstants.OPERATION_DELETE, text1.substring(commonPrefix, text1.length() - commonSuffix), 
                text1.length() - commonPrefix - commonSuffix, DiffConstants.STATUS_DELETED, DiffConstants.CSS_CLASS_DELETED));
            
            diffDetails.add(createDiffDetail(DiffConstants.OPERATION_INSERT, text2.substring(commonPrefix, text2.length() - commonSuffix), 
                text2.length() - commonPrefix - commonSuffix, DiffConstants.STATUS_ADDED, DiffConstants.CSS_CLASS_ADDED));
        }
        
        // Add suffix
        if (commonSuffix > 0) {
            diffDetails.add(createDiffDetail(DiffConstants.OPERATION_EQUAL, text1.substring(text1.length() - commonSuffix), 
                commonSuffix, DiffConstants.STATUS_UNCHANGED, DiffConstants.CSS_CLASS_UNCHANGED));
        }
    }
    
    /**
     * Builds complete character diff for different texts
     * 
     * @param text1 the first text
     * @param text2 the second text
     * @param diffDetails the list to add diff details to
     */
    private void buildCompleteCharacterDiff(String text1, String text2, List<Map<String, Object>> diffDetails) {
        if (!text1.isEmpty()) {
            diffDetails.add(createDiffDetail(DiffConstants.OPERATION_DELETE, text1, text1.length(), DiffConstants.STATUS_DELETED, DiffConstants.CSS_CLASS_DELETED));
        }
        
        if (!text2.isEmpty()) {
            diffDetails.add(createDiffDetail(DiffConstants.OPERATION_INSERT, text2, text2.length(), DiffConstants.STATUS_ADDED, DiffConstants.CSS_CLASS_ADDED));
        }
    }
    
    /**
     * Creates a diff detail map
     * 
     * @param operation the operation type
     * @param text the text content
     * @param length the text length
     * @param type the diff type
     * @param cssClass the CSS class
     * @return the diff detail map
     */
    private Map<String, Object> createDiffDetail(String operation, String text, int length, String type, String cssClass) {
        Map<String, Object> detail = new HashMap<>();
        detail.put(DiffConstants.KEY_OPERATION, operation);
        detail.put(DiffConstants.KEY_TEXT, text);
        detail.put(DiffConstants.KEY_LENGTH, length);
        detail.put(DiffConstants.KEY_TYPE, type);
        detail.put(DiffConstants.KEY_CSS_CLASS, cssClass);
        return detail;
    }
    
    /**
     * Generates JSON structural diff
     * 
     * @param json1 the first JSON string
     * @param json2 the second JSON string
     * @return the JSON diff map
     */
    private Map<String, Object> generateJsonDiff(String json1, String json2) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            JsonNode node1 = objectMapper.readTree(json1);
            JsonNode node2 = objectMapper.readTree(json2);
            
            result.put(DiffConstants.KEY_IDENTICAL, node1.equals(node2));
            result.put(DiffConstants.KEY_TYPE, DiffConstants.DIFF_TYPE_JSON);
            
            if (!node1.equals(node2)) {
                // Generate structural diff
                List<Map<String, Object>> structuralChanges = compareJsonNodes(node1, node2, "");
                result.put(DiffConstants.KEY_STRUCTURAL_CHANGES, structuralChanges);
                
                // Generate formatted JSON diff
                String formattedJson1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node1);
                String formattedJson2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node2);
                
                List<String> lines1 = Arrays.asList(formattedJson1.split("\n", -1));
                List<String> lines2 = Arrays.asList(formattedJson2.split("\n", -1));
                
                String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, 3);
                result.put(DiffConstants.KEY_UNIFIED_DIFF, unifiedDiff);
            }
            
        } catch (Exception e) {
            log.warn("Error parsing JSON: {}", e.getMessage());
            result.put(DiffConstants.KEY_ERROR, DiffConstants.INVALID_JSON_FORMAT_MESSAGE + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * Compares JSON nodes recursively
     * 
     * @param node1 the first JSON node
     * @param node2 the second JSON node
     * @param path the current path in the JSON structure
     * @return list of structural changes
     */
    private List<Map<String, Object>> compareJsonNodes(JsonNode node1, JsonNode node2, String path) {
        List<Map<String, Object>> changes = new ArrayList<>();
        
        if (node1.getNodeType() != node2.getNodeType()) {
            changes.add(createJsonChange(path, DiffConstants.JSON_CHANGE_TYPE_CHANGED, node1.toString(), node2.toString()));
            return changes;
        }
        
        if (node1.isObject()) {
            changes.addAll(compareJsonObject(node1, node2, path));
        } else if (node1.isArray()) {
            changes.addAll(compareJsonArray(node1, node2, path));
        } else if (!node1.equals(node2)) {
            changes.add(createJsonChange(path, DiffConstants.JSON_CHANGE_VALUE_CHANGED, node1.toString(), node2.toString()));
        }
        
        return changes;
    }
    
    /**
     * Compares JSON objects
     * 
     * @param node1 the first object node
     * @param node2 the second object node
     * @param path the current path
     * @return list of changes
     */
    private List<Map<String, Object>> compareJsonObject(JsonNode node1, JsonNode node2, String path) {
        List<Map<String, Object>> changes = new ArrayList<>();
        
        // Check fields in node1
        Iterator<String> fieldNames1 = node1.fieldNames();
        while (fieldNames1.hasNext()) {
            String fieldName = fieldNames1.next();
            String newPath = path.isEmpty() ? fieldName : path + "." + fieldName;
            
                            if (node2.has(fieldName)) {
                    changes.addAll(compareJsonNodes(node1.get(fieldName), node2.get(fieldName), newPath));
                } else {
                    changes.add(createJsonChange(newPath, DiffConstants.JSON_CHANGE_FIELD_REMOVED, node1.get(fieldName).toString(), null));
                }
            }
            
            // Check fields in node2
            Iterator<String> fieldNames2 = node2.fieldNames();
            while (fieldNames2.hasNext()) {
                String fieldName = fieldNames2.next();
                if (!node1.has(fieldName)) {
                    String newPath = path.isEmpty() ? fieldName : path + "." + fieldName;
                    changes.add(createJsonChange(newPath, DiffConstants.JSON_CHANGE_FIELD_ADDED, null, node2.get(fieldName).toString()));
                }
            }
        
        return changes;
    }
    
    /**
     * Compares JSON arrays
     * 
     * @param node1 the first array node
     * @param node2 the second array node
     * @param path the current path
     * @return list of changes
     */
    private List<Map<String, Object>> compareJsonArray(JsonNode node1, JsonNode node2, String path) {
        List<Map<String, Object>> changes = new ArrayList<>();
        
        int size1 = node1.size();
        int size2 = node2.size();
        int maxSize = Math.max(size1, size2);
        
        for (int i = 0; i < maxSize; i++) {
            String newPath = path + "[" + i + "]";
                            if (i < size1 && i < size2) {
                    changes.addAll(compareJsonNodes(node1.get(i), node2.get(i), newPath));
                } else if (i < size1) {
                    changes.add(createJsonChange(newPath, DiffConstants.JSON_CHANGE_ARRAY_ELEMENT_REMOVED, node1.get(i).toString(), null));
                } else {
                    changes.add(createJsonChange(newPath, DiffConstants.JSON_CHANGE_ARRAY_ELEMENT_ADDED, null, node2.get(i).toString()));
                }
        }
        
        return changes;
    }
    
    /**
     * Creates a JSON change map
     * 
     * @param path the JSON path
     * @param type the change type
     * @param oldValue the old value
     * @param newValue the new value
     * @return the change map
     */
    private Map<String, Object> createJsonChange(String path, String type, String oldValue, String newValue) {
        Map<String, Object> change = new HashMap<>();
        change.put(DiffConstants.KEY_PATH, path);
        change.put(DiffConstants.KEY_TYPE, type);
        if (oldValue != null) change.put(DiffConstants.KEY_OLD_VALUE, oldValue);
        if (newValue != null) change.put(DiffConstants.KEY_NEW_VALUE, newValue);
        return change;
    }
    
    /**
     * Generates XML diff (simplified implementation)
     * 
     * @param xml1 the first XML string
     * @param xml2 the second XML string
     * @return the XML diff map
     */
    private Map<String, Object> generateXmlDiff(String xml1, String xml2) {
        Map<String, Object> result = new HashMap<>();
        
        // For now, treat XML as text diff
        // In production, you might want to use a proper XML diff library
        result.put(DiffConstants.KEY_IDENTICAL, xml1.equals(xml2));
        result.put(DiffConstants.KEY_TYPE, DiffConstants.DIFF_TYPE_XML);
        result.put(DiffConstants.KEY_NOTE, DiffConstants.XML_DIFF_NOTE_MESSAGE);
        
        if (!xml1.equals(xml2)) {
            List<String> lines1 = Arrays.asList(xml1.split("\n", -1));
            List<String> lines2 = Arrays.asList(xml2.split("\n", -1));
            
            String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, 3);
            result.put(DiffConstants.KEY_UNIFIED_DIFF, unifiedDiff);
        }
        
        return result;
    }
    
    /**
     * Generates code-aware diff
     * 
     * @param code1 the first code string
     * @param code2 the second code string
     * @param request the diff request
     * @return the code diff map
     */
    private Map<String, Object> generateCodeDiff(String code1, String code2, DiffRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // Normalize code by removing comments and extra whitespace
        String normalizedCode1 = normalizeCode(code1);
        String normalizedCode2 = normalizeCode(code2);
        
        result.put(DiffConstants.KEY_IDENTICAL, normalizedCode1.equals(normalizedCode2));
        result.put(DiffConstants.KEY_TYPE, DiffConstants.DIFF_TYPE_CODE);
        result.put(DiffConstants.KEY_ORIGINAL_IDENTICAL, code1.equals(code2));
        
        if (!normalizedCode1.equals(normalizedCode2)) {
            List<String> lines1 = Arrays.asList(code1.split("\n", -1));
            List<String> lines2 = Arrays.asList(code2.split("\n", -1));
            
            String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, request.getContextLines());
            result.put(DiffConstants.KEY_UNIFIED_DIFF, unifiedDiff);
            
            // Add code-specific statistics
            Map<String, Object> codeStats = buildCodeStatistics(code1, code2);
            result.put(DiffConstants.KEY_CODE_STATISTICS, codeStats);
        }
        
        return result;
    }
    
    /**
     * Normalizes code by removing comments and extra whitespace
     * 
     * @param code the code to normalize
     * @return the normalized code
     */
    private String normalizeCode(String code) {
        // Remove single-line comments
        String normalized = code.replaceAll("(?m)//.*$", "");
        // Remove multi-line comments (simplified)
        normalized = normalized.replaceAll("(?s)/\\*.*?\\*/", "");
        // Normalize whitespace
        normalized = normalized.replaceAll("\\s+", " ").trim();
        return normalized;
    }
    
    /**
     * Builds code-specific statistics
     * 
     * @param code1 the first code
     * @param code2 the second code
     * @return the code statistics map
     */
    private Map<String, Object> buildCodeStatistics(String code1, String code2) {
        Map<String, Object> codeStats = new HashMap<>();
        codeStats.put(DiffConstants.KEY_LINES_OF_CODE_1, countLinesOfCode(code1));
        codeStats.put(DiffConstants.KEY_LINES_OF_CODE_2, countLinesOfCode(code2));
        codeStats.put(DiffConstants.KEY_COMMENT_LINES_1, countCommentLines(code1));
        codeStats.put(DiffConstants.KEY_COMMENT_LINES_2, countCommentLines(code2));
        return codeStats;
    }
    
    /**
     * Counts lines of code (excluding comments and empty lines)
     * 
     * @param code the code to analyze
     * @return the number of lines of code
     */
    private int countLinesOfCode(String code) {
        return (int) Arrays.stream(code.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty() && !line.startsWith("//") && !line.startsWith("/*"))
                .count();
    }
    
    /**
     * Counts comment lines
     * 
     * @param code the code to analyze
     * @return the number of comment lines
     */
    private int countCommentLines(String code) {
        return (int) Arrays.stream(code.split("\n"))
                .map(String::trim)
                .filter(line -> line.startsWith("//") || line.startsWith("/*") || line.startsWith("*"))
                .count();
    }
    
    /**
     * Calculates comprehensive statistics for the diff
     * 
     * @param lines1 the first set of lines
     * @param lines2 the second set of lines
     * @param diffDetails the character-level diff details
     * @return the statistics map
     */
    private Map<String, Object> calculateEnhancedStatistics(List<String> lines1, List<String> lines2, 
                                                           List<Map<String, Object>> diffDetails) {
        Map<String, Object> stats = new HashMap<>();
        
        // Line statistics
        LineStatistics lineStats = calculateLineStatistics(lines1, lines2);
        stats.put(DiffConstants.KEY_TOTAL_LINES_1, lines1.size());
        stats.put(DiffConstants.KEY_TOTAL_LINES_2, lines2.size());
        stats.put(DiffConstants.KEY_UNCHANGED_LINES, lineStats.unchangedLines);
        stats.put(DiffConstants.KEY_CHANGED_LINES, lineStats.changedLines);
        stats.put(DiffConstants.KEY_CHANGE_PERCENTAGE, lineStats.changePercentage);
        
        // Character statistics
        CharacterStatistics charStats = calculateCharacterStatistics(diffDetails);
        stats.put(DiffConstants.KEY_ADDED_CHARACTERS, charStats.addedChars);
        stats.put(DiffConstants.KEY_DELETED_CHARACTERS, charStats.deletedChars);
        stats.put(DiffConstants.KEY_UNCHANGED_CHARACTERS, charStats.unchangedChars);
        stats.put(DiffConstants.KEY_TOTAL_CHANGES, charStats.totalChanges);
        
        // Similarity metrics
        if (charStats.totalChanges + charStats.unchangedChars > 0) {
            double similarity = (double) charStats.unchangedChars / (charStats.totalChanges + charStats.unchangedChars);
            stats.put(DiffConstants.KEY_SIMILARITY, similarity);
            stats.put(DiffConstants.KEY_SIMILARITY_PERCENTAGE, similarity * 100);
        }
        
        return stats;
    }
    
    /**
     * Calculates line-level statistics
     * 
     * @param lines1 the first set of lines
     * @param lines2 the second set of lines
     * @return the line statistics
     */
    private LineStatistics calculateLineStatistics(List<String> lines1, List<String> lines2) {
        int unchangedLines = 0;
        int changedLines = 0;
        
        for (int i = 0; i < Math.min(lines1.size(), lines2.size()); i++) {
            if (lines1.get(i).equals(lines2.get(i))) {
                unchangedLines++;
            } else {
                changedLines++;
            }
        }
        
        changedLines += Math.abs(lines1.size() - lines2.size());
        double changePercentage = lines1.size() > 0 ? (double) changedLines / lines1.size() * 100 : 0;
        
        return new LineStatistics(unchangedLines, changedLines, changePercentage);
    }
    
    /**
     * Calculates character-level statistics
     * 
     * @param diffDetails the character-level diff details
     * @return the character statistics
     */
    private CharacterStatistics calculateCharacterStatistics(List<Map<String, Object>> diffDetails) {
        int addedChars = 0;
        int deletedChars = 0;
        int unchangedChars = 0;
        
        for (Map<String, Object> diff : diffDetails) {
            String operation = (String) diff.get(DiffConstants.KEY_OPERATION);
            int length = (Integer) diff.get(DiffConstants.KEY_LENGTH);
            
            switch (operation) {
                case DiffConstants.OPERATION_INSERT:
                    addedChars += length;
                    break;
                case DiffConstants.OPERATION_DELETE:
                    deletedChars += length;
                    break;
                case DiffConstants.OPERATION_EQUAL:
                    unchangedChars += length;
                    break;
            }
        }
        
        return new CharacterStatistics(addedChars, deletedChars, unchangedChars, addedChars + deletedChars);
    }
    
    /**
     * Immutable data class for line statistics
     */
    private static class LineStatistics {
        final int unchangedLines;
        final int changedLines;
        final double changePercentage;
        
        LineStatistics(int unchangedLines, int changedLines, double changePercentage) {
            this.unchangedLines = unchangedLines;
            this.changedLines = changedLines;
            this.changePercentage = changePercentage;
        }
    }
    
    /**
     * Immutable data class for character statistics
     */
    private static class CharacterStatistics {
        final int addedChars;
        final int deletedChars;
        final int unchangedChars;
        final int totalChanges;
        
        CharacterStatistics(int addedChars, int deletedChars, int unchangedChars, int totalChanges) {
            this.addedChars = addedChars;
            this.deletedChars = deletedChars;
            this.unchangedChars = unchangedChars;
            this.totalChanges = totalChanges;
        }
    }
} 