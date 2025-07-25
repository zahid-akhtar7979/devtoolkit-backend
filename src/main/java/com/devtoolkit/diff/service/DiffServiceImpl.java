package com.devtoolkit.diff.service;

import com.devtoolkit.diff.dto.DiffRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class DiffServiceImpl implements DiffService {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    
    @Override
    public Map<String, Object> compareText(String text1, String text2) {
        // Basic text comparison for backward compatibility
        Map<String, Object> result = new HashMap<>();
        
        if (text1 == null) text1 = "";
        if (text2 == null) text2 = "";
        
        result.put("identical", text1.equals(text2));
        result.put("length1", text1.length());
        result.put("length2", text2.length());
        
        if (text1.equals(text2)) {
            result.put("differences", "No differences found");
        } else {
            result.put("differences", "Found differences");
        }
        
        return result;
    }
    
    @Override
    public Map<String, Object> generateEnhancedDiff(DiffRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        String text1 = preprocessText(request.getText1(), request);
        String text2 = preprocessText(request.getText2(), request);
        
        // Basic comparison
        result.put("identical", text1.equals(text2));
        result.put("length1", request.getText1() != null ? request.getText1().length() : 0);
        result.put("length2", request.getText2() != null ? request.getText2().length() : 0);
        
        if (text1.equals(text2)) {
            result.put("differences", "No differences found");
            result.put("diffDetails", new ArrayList<>());
            result.put("unifiedDiff", "");
            result.put("sideBySide", new HashMap<>());
            result.put("statistics", createEmptyStatistics());
            return result;
        }
        
        // Generate different types of diffs based on request
        switch (request.getDiffType()) {
            case JSON:
                result.putAll(generateJsonDiff(text1, text2));
                break;
            case XML:
                result.putAll(generateXmlDiff(text1, text2));
                break;
            case CODE:
                result.putAll(generateCodeDiff(text1, text2, request));
                break;
            default:
                result.putAll(generateTextDiff(text1, text2, request));
        }
        
        return result;
    }
    
    /**
     * Preprocess text based on diff options
     */
    private String preprocessText(String text, DiffRequest request) {
        if (text == null) return "";
        
        String processed = text;
        
        if (request.isIgnoreLineEndings()) {
            processed = processed.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
        }
        
        if (request.isIgnoreWhitespace()) {
            processed = processed.replaceAll("\\s+", " ").trim();
        }
        
        if (request.isIgnoreCase()) {
            processed = processed.toLowerCase();
        }
        
        return processed;
    }
    
    /**
     * Generate advanced text diff using java-diff-utils
     */
    private Map<String, Object> generateTextDiff(String text1, String text2, DiffRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // Line-by-line diff using java-diff-utils
        List<String> lines1 = Arrays.asList(text1.split("\n", -1));
        List<String> lines2 = Arrays.asList(text2.split("\n", -1));
        
        // Generate unified diff with context
        String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, request.getContextLines());
        result.put("unifiedDiff", unifiedDiff);
        
        // Generate side-by-side comparison with highlighting
        Map<String, Object> sideBySide = generateEnhancedSideBySideDiff(lines1, lines2);
        result.put("sideBySide", sideBySide);
        
        // Generate character-level diff with Levenshtein distance
        List<Map<String, Object>> diffDetails = generateCharacterLevelDiff(text1, text2);
        result.put("diffDetails", diffDetails);
        
        // Calculate comprehensive statistics
        Map<String, Object> stats = calculateEnhancedStatistics(lines1, lines2, diffDetails);
        result.put("statistics", stats);
        
        return result;
    }
    
    /**
     * Generate context-aware unified diff
     */
    private String generateContextAwareUnifiedDiff(List<String> lines1, List<String> lines2, int contextLines) {
        StringBuilder diff = new StringBuilder();
        List<Integer> changedLines = new ArrayList<>();
        
        // Find changed lines
        int maxLines = Math.max(lines1.size(), lines2.size());
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < lines1.size() ? lines1.get(i) : "";
            String line2 = i < lines2.size() ? lines2.get(i) : "";
            if (!line1.equals(line2)) {
                changedLines.add(i);
            }
        }
        
        if (changedLines.isEmpty()) {
            return "";
        }
        
        // Group consecutive changes
        List<List<Integer>> changeGroups = groupConsecutiveChanges(changedLines, contextLines);
        
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
     * Group consecutive changes for context-aware diff
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
     * Generate enhanced side-by-side diff with highlighting
     */
    private Map<String, Object> generateEnhancedSideBySideDiff(List<String> lines1, List<String> lines2) {
        Map<String, Object> sideBySide = new HashMap<>();
        List<Map<String, Object>> comparison = new ArrayList<>();
        
        int maxLines = Math.max(lines1.size(), lines2.size());
        
        for (int i = 0; i < maxLines; i++) {
            Map<String, Object> lineComparison = new HashMap<>();
            String line1 = i < lines1.size() ? lines1.get(i) : "";
            String line2 = i < lines2.size() ? lines2.get(i) : "";
            
            lineComparison.put("lineNumber", i + 1);
            lineComparison.put("left", line1);
            lineComparison.put("right", line2);
            
            if (line1.equals(line2)) {
                lineComparison.put("status", "unchanged");
                lineComparison.put("cssClass", "diff-unchanged");
            } else {
                lineComparison.put("status", "modified");
                lineComparison.put("cssClass", "diff-modified");
                
                // Add character-level highlighting
                List<Map<String, Object>> leftHighlights = generateCharacterHighlights(line1, line2);
                List<Map<String, Object>> rightHighlights = generateCharacterHighlights(line2, line1);
                
                lineComparison.put("leftHighlights", leftHighlights);
                lineComparison.put("rightHighlights", rightHighlights);
            }
            
            comparison.add(lineComparison);
        }
        
        sideBySide.put("comparison", comparison);
        sideBySide.put("totalLines", maxLines);
        
        return sideBySide;
    }
    
    /**
     * Generate character-level highlights for side-by-side comparison
     */
    private List<Map<String, Object>> generateCharacterHighlights(String text1, String text2) {
        List<Map<String, Object>> highlights = new ArrayList<>();
        
        // Use Levenshtein distance to find similar substrings
        int distance = levenshteinDistance.apply(text1, text2);
        double similarity = 1.0 - (double) distance / Math.max(text1.length(), text2.length());
        
        if (similarity > 0.5) {
            // Find common prefixes and suffixes
            int commonPrefix = findCommonPrefix(text1, text2);
            int commonSuffix = findCommonSuffix(text1, text2);
            
            if (commonPrefix > 0) {
                Map<String, Object> prefix = new HashMap<>();
                prefix.put("start", 0);
                prefix.put("end", commonPrefix);
                prefix.put("type", "unchanged");
                prefix.put("cssClass", "diff-unchanged");
                highlights.add(prefix);
            }
            
            if (commonPrefix < text1.length() - commonSuffix) {
                Map<String, Object> middle = new HashMap<>();
                middle.put("start", commonPrefix);
                middle.put("end", text1.length() - commonSuffix);
                middle.put("type", "changed");
                middle.put("cssClass", "diff-changed");
                highlights.add(middle);
            }
            
            if (commonSuffix > 0) {
                Map<String, Object> suffix = new HashMap<>();
                suffix.put("start", text1.length() - commonSuffix);
                suffix.put("end", text1.length());
                suffix.put("type", "unchanged");
                suffix.put("cssClass", "diff-unchanged");
                highlights.add(suffix);
            }
        } else {
            // Completely different
            Map<String, Object> highlight = new HashMap<>();
            highlight.put("start", 0);
            highlight.put("end", text1.length());
            highlight.put("type", "deleted");
            highlight.put("cssClass", "diff-deleted");
            highlights.add(highlight);
        }
        
        return highlights;
    }
    
    private int findCommonPrefix(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());
        for (int i = 0; i < minLength; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return i;
            }
        }
        return minLength;
    }
    
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
     * Generate character-level diff with advanced algorithms
     */
    private List<Map<String, Object>> generateCharacterLevelDiff(String text1, String text2) {
        List<Map<String, Object>> diffDetails = new ArrayList<>();
        
        // Use Levenshtein distance for character-level diff
        int distance = levenshteinDistance.apply(text1, text2);
        double similarity = 1.0 - (double) distance / Math.max(text1.length(), text2.length());
        
        if (similarity > 0.8) {
            // High similarity - find common parts
            int commonPrefix = findCommonPrefix(text1, text2);
            int commonSuffix = findCommonSuffix(text1, text2);
            
            if (commonPrefix > 0) {
                Map<String, Object> prefix = new HashMap<>();
                prefix.put("operation", "EQUAL");
                prefix.put("text", text1.substring(0, commonPrefix));
                prefix.put("length", commonPrefix);
                prefix.put("type", "unchanged");
                prefix.put("cssClass", "diff-unchanged");
                diffDetails.add(prefix);
            }
            
            if (commonPrefix < text1.length() - commonSuffix) {
                Map<String, Object> middle1 = new HashMap<>();
                middle1.put("operation", "DELETE");
                middle1.put("text", text1.substring(commonPrefix, text1.length() - commonSuffix));
                middle1.put("length", text1.length() - commonPrefix - commonSuffix);
                middle1.put("type", "deleted");
                middle1.put("cssClass", "diff-deleted");
                diffDetails.add(middle1);
                
                Map<String, Object> middle2 = new HashMap<>();
                middle2.put("operation", "INSERT");
                middle2.put("text", text2.substring(commonPrefix, text2.length() - commonSuffix));
                middle2.put("length", text2.length() - commonPrefix - commonSuffix);
                middle2.put("type", "added");
                middle2.put("cssClass", "diff-added");
                diffDetails.add(middle2);
            }
            
            if (commonSuffix > 0) {
                Map<String, Object> suffix = new HashMap<>();
                suffix.put("operation", "EQUAL");
                suffix.put("text", text1.substring(text1.length() - commonSuffix));
                suffix.put("length", commonSuffix);
                suffix.put("type", "unchanged");
                suffix.put("cssClass", "diff-unchanged");
                diffDetails.add(suffix);
            }
        } else {
            // Low similarity - treat as completely different
            if (!text1.isEmpty()) {
                Map<String, Object> deleted = new HashMap<>();
                deleted.put("operation", "DELETE");
                deleted.put("text", text1);
                deleted.put("length", text1.length());
                deleted.put("type", "deleted");
                deleted.put("cssClass", "diff-deleted");
                diffDetails.add(deleted);
            }
            
            if (!text2.isEmpty()) {
                Map<String, Object> added = new HashMap<>();
                added.put("operation", "INSERT");
                added.put("text", text2);
                added.put("length", text2.length());
                added.put("type", "added");
                added.put("cssClass", "diff-added");
                diffDetails.add(added);
            }
        }
        
        return diffDetails;
    }
    
    /**
     * Generate JSON structural diff
     */
    private Map<String, Object> generateJsonDiff(String json1, String json2) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            JsonNode node1 = objectMapper.readTree(json1);
            JsonNode node2 = objectMapper.readTree(json2);
            
            result.put("identical", node1.equals(node2));
            result.put("type", "json");
            
            if (!node1.equals(node2)) {
                // Generate structural diff
                List<Map<String, Object>> structuralChanges = compareJsonNodes(node1, node2, "");
                result.put("structuralChanges", structuralChanges);
                
                // Also generate text diff for the formatted JSON
                String formattedJson1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node1);
                String formattedJson2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node2);
                
                List<String> lines1 = Arrays.asList(formattedJson1.split("\n", -1));
                List<String> lines2 = Arrays.asList(formattedJson2.split("\n", -1));
                
                String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, 3);
                result.put("unifiedDiff", unifiedDiff);
            }
            
        } catch (Exception e) {
            result.put("error", "Invalid JSON format: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * Compare JSON nodes recursively
     */
    private List<Map<String, Object>> compareJsonNodes(JsonNode node1, JsonNode node2, String path) {
        List<Map<String, Object>> changes = new ArrayList<>();
        
        if (node1.getNodeType() != node2.getNodeType()) {
            Map<String, Object> change = new HashMap<>();
            change.put("path", path);
            change.put("type", "TYPE_CHANGED");
            change.put("oldValue", node1.toString());
            change.put("newValue", node2.toString());
            changes.add(change);
            return changes;
        }
        
        if (node1.isObject()) {
            Iterator<String> fieldNames1 = node1.fieldNames();
            while (fieldNames1.hasNext()) {
                String fieldName = fieldNames1.next();
                String newPath = path.isEmpty() ? fieldName : path + "." + fieldName;
                
                if (node2.has(fieldName)) {
                    changes.addAll(compareJsonNodes(node1.get(fieldName), node2.get(fieldName), newPath));
                } else {
                    Map<String, Object> change = new HashMap<>();
                    change.put("path", newPath);
                    change.put("type", "FIELD_REMOVED");
                    change.put("oldValue", node1.get(fieldName).toString());
                    changes.add(change);
                }
            }
            
            Iterator<String> fieldNames2 = node2.fieldNames();
            while (fieldNames2.hasNext()) {
                String fieldName = fieldNames2.next();
                if (!node1.has(fieldName)) {
                    String newPath = path.isEmpty() ? fieldName : path + "." + fieldName;
                    Map<String, Object> change = new HashMap<>();
                    change.put("path", newPath);
                    change.put("type", "FIELD_ADDED");
                    change.put("newValue", node2.get(fieldName).toString());
                    changes.add(change);
                }
            }
        } else if (node1.isArray()) {
            int size1 = node1.size();
            int size2 = node2.size();
            int maxSize = Math.max(size1, size2);
            
            for (int i = 0; i < maxSize; i++) {
                String newPath = path + "[" + i + "]";
                if (i < size1 && i < size2) {
                    changes.addAll(compareJsonNodes(node1.get(i), node2.get(i), newPath));
                } else if (i < size1) {
                    Map<String, Object> change = new HashMap<>();
                    change.put("path", newPath);
                    change.put("type", "ARRAY_ELEMENT_REMOVED");
                    change.put("oldValue", node1.get(i).toString());
                    changes.add(change);
                } else {
                    Map<String, Object> change = new HashMap<>();
                    change.put("path", newPath);
                    change.put("type", "ARRAY_ELEMENT_ADDED");
                    change.put("newValue", node2.get(i).toString());
                    changes.add(change);
                }
            }
        } else if (!node1.equals(node2)) {
            Map<String, Object> change = new HashMap<>();
            change.put("path", path);
            change.put("type", "VALUE_CHANGED");
            change.put("oldValue", node1.toString());
            change.put("newValue", node2.toString());
            changes.add(change);
        }
        
        return changes;
    }
    
    /**
     * Generate XML diff (simplified)
     */
    private Map<String, Object> generateXmlDiff(String xml1, String xml2) {
        Map<String, Object> result = new HashMap<>();
        
        // For now, treat XML as text diff
        // In production, you might want to use a proper XML diff library
        result.put("identical", xml1.equals(xml2));
        result.put("type", "xml");
        result.put("note", "XML diff is currently treated as text diff");
        
        if (!xml1.equals(xml2)) {
            List<String> lines1 = Arrays.asList(xml1.split("\n", -1));
            List<String> lines2 = Arrays.asList(xml2.split("\n", -1));
            
            String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, 3);
            result.put("unifiedDiff", unifiedDiff);
        }
        
        return result;
    }
    
    /**
     * Generate code-aware diff
     */
    private Map<String, Object> generateCodeDiff(String code1, String code2, DiffRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // Remove comments and normalize whitespace for code comparison
        String normalizedCode1 = normalizeCode(code1);
        String normalizedCode2 = normalizeCode(code2);
        
        result.put("identical", normalizedCode1.equals(normalizedCode2));
        result.put("type", "code");
        result.put("originalIdentical", code1.equals(code2));
        
        if (!normalizedCode1.equals(normalizedCode2)) {
            List<String> lines1 = Arrays.asList(code1.split("\n", -1));
            List<String> lines2 = Arrays.asList(code2.split("\n", -1));
            
            String unifiedDiff = generateContextAwareUnifiedDiff(lines1, lines2, request.getContextLines());
            result.put("unifiedDiff", unifiedDiff);
            
            // Add code-specific statistics
            Map<String, Object> codeStats = new HashMap<>();
            codeStats.put("linesOfCode1", countLinesOfCode(code1));
            codeStats.put("linesOfCode2", countLinesOfCode(code2));
            codeStats.put("commentLines1", countCommentLines(code1));
            codeStats.put("commentLines2", countCommentLines(code2));
            result.put("codeStatistics", codeStats);
        }
        
        return result;
    }
    
    /**
     * Normalize code by removing comments and extra whitespace
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
    
    private int countLinesOfCode(String code) {
        return (int) Arrays.stream(code.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty() && !line.startsWith("//") && !line.startsWith("/*"))
                .count();
    }
    
    private int countCommentLines(String code) {
        return (int) Arrays.stream(code.split("\n"))
                .map(String::trim)
                .filter(line -> line.startsWith("//") || line.startsWith("/*") || line.startsWith("*"))
                .count();
    }
    
    /**
     * Calculate enhanced statistics
     */
    private Map<String, Object> calculateEnhancedStatistics(List<String> lines1, List<String> lines2, 
                                                           List<Map<String, Object>> diffDetails) {
        Map<String, Object> stats = new HashMap<>();
        
        // Line statistics
        stats.put("totalLines1", lines1.size());
        stats.put("totalLines2", lines2.size());
        
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
        
        stats.put("unchangedLines", unchangedLines);
        stats.put("changedLines", changedLines);
        stats.put("changePercentage", lines1.size() > 0 ? 
            (double) changedLines / lines1.size() * 100 : 0);
        
        // Character statistics
        int addedChars = 0;
        int deletedChars = 0;
        int unchangedChars = 0;
        
        for (Map<String, Object> diff : diffDetails) {
            String operation = (String) diff.get("operation");
            int length = (Integer) diff.get("length");
            
            switch (operation) {
                case "INSERT":
                    addedChars += length;
                    break;
                case "DELETE":
                    deletedChars += length;
                    break;
                case "EQUAL":
                    unchangedChars += length;
                    break;
            }
        }
        
        stats.put("addedCharacters", addedChars);
        stats.put("deletedCharacters", deletedChars);
        stats.put("unchangedCharacters", unchangedChars);
        stats.put("totalChanges", addedChars + deletedChars);
        
        // Similarity metrics
        if (addedChars + deletedChars + unchangedChars > 0) {
            double similarity = (double) unchangedChars / (addedChars + deletedChars + unchangedChars);
            stats.put("similarity", similarity);
            stats.put("similarityPercentage", similarity * 100);
        }
        
        return stats;
    }
    
    private Map<String, Object> createEmptyStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalLines1", 0);
        stats.put("totalLines2", 0);
        stats.put("unchangedLines", 0);
        stats.put("changedLines", 0);
        stats.put("changePercentage", 0.0);
        stats.put("addedCharacters", 0);
        stats.put("deletedCharacters", 0);
        stats.put("unchangedCharacters", 0);
        stats.put("totalChanges", 0);
        stats.put("similarity", 1.0);
        stats.put("similarityPercentage", 100.0);
        return stats;
    }
} 