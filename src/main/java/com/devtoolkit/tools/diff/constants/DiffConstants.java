package com.devtoolkit.tools.diff.constants;

/**
 * Constants for Diff operations
 */
public final class DiffConstants {
    
    private DiffConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Operations
    public static final String COMPARE_OPERATION = "compare";
    public static final String ENHANCED_COMPARE_OPERATION = "enhanced_compare";
    
    // Error messages
    public static final String EMPTY_TEXT_MESSAGE = "Text cannot be null or empty";
    public static final String BOTH_TEXTS_REQUIRED_MESSAGE = "Both texts are required for comparison";
    public static final String COMPARE_SUCCESS_MESSAGE = "Text comparison completed successfully";
    public static final String ENHANCED_COMPARE_SUCCESS_MESSAGE = "Enhanced comparison completed successfully";
    public static final String INVALID_JSON_FORMAT_MESSAGE = "Invalid JSON format: ";
    public static final String XML_DIFF_NOTE_MESSAGE = "XML diff is currently treated as text diff";
    
    // Default values
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final int DEFAULT_CONTEXT_LINES = 3;
    
    // Response keys
    public static final String KEY_UNIFIED_DIFF = "unifiedDiff";
    public static final String KEY_SIDE_BY_SIDE = "sideBySide";
    public static final String KEY_DIFF_DETAILS = "diffDetails";
    public static final String KEY_STATISTICS = "statistics";
    public static final String KEY_COMPARISON = "comparison";
    public static final String KEY_TOTAL_LINES = "totalLines";
    public static final String KEY_LINE_NUMBER = "lineNumber";
    public static final String KEY_LEFT = "left";
    public static final String KEY_RIGHT = "right";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CSS_CLASS = "cssClass";
    public static final String KEY_LEFT_HIGHLIGHTS = "leftHighlights";
    public static final String KEY_RIGHT_HIGHLIGHTS = "rightHighlights";
    public static final String KEY_START = "start";
    public static final String KEY_END = "end";
    public static final String KEY_TYPE = "type";
    public static final String KEY_OPERATION = "operation";
    public static final String KEY_TEXT = "text";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_IDENTICAL = "identical";
    public static final String KEY_STRUCTURAL_CHANGES = "structuralChanges";
    public static final String KEY_ERROR = "error";
    public static final String KEY_PATH = "path";
    public static final String KEY_OLD_VALUE = "oldValue";
    public static final String KEY_NEW_VALUE = "newValue";
    public static final String KEY_NOTE = "note";
    public static final String KEY_ORIGINAL_IDENTICAL = "originalIdentical";
    public static final String KEY_CODE_STATISTICS = "codeStatistics";
    public static final String KEY_TOTAL_LINES_1 = "totalLines1";
    public static final String KEY_TOTAL_LINES_2 = "totalLines2";
    public static final String KEY_UNCHANGED_LINES = "unchangedLines";
    public static final String KEY_CHANGED_LINES = "changedLines";
    public static final String KEY_CHANGE_PERCENTAGE = "changePercentage";
    public static final String KEY_ADDED_CHARACTERS = "addedCharacters";
    public static final String KEY_DELETED_CHARACTERS = "deletedCharacters";
    public static final String KEY_UNCHANGED_CHARACTERS = "unchangedCharacters";
    public static final String KEY_TOTAL_CHANGES = "totalChanges";
    public static final String KEY_SIMILARITY = "similarity";
    public static final String KEY_SIMILARITY_PERCENTAGE = "similarityPercentage";
    
    // Status values
    public static final String STATUS_UNCHANGED = "unchanged";
    public static final String STATUS_MODIFIED = "modified";
    public static final String STATUS_CHANGED = "changed";
    public static final String STATUS_DELETED = "deleted";
    public static final String STATUS_ADDED = "added";
    
    // CSS classes
    public static final String CSS_CLASS_UNCHANGED = "diff-unchanged";
    public static final String CSS_CLASS_MODIFIED = "diff-modified";
    public static final String CSS_CLASS_CHANGED = "diff-changed";
    public static final String CSS_CLASS_DELETED = "diff-deleted";
    public static final String CSS_CLASS_ADDED = "diff-added";
    
    // Operation types
    public static final String OPERATION_EQUAL = "EQUAL";
    public static final String OPERATION_DELETE = "DELETE";
    public static final String OPERATION_INSERT = "INSERT";
    
    // Diff types
    public static final String DIFF_TYPE_JSON = "json";
    public static final String DIFF_TYPE_XML = "xml";
    public static final String DIFF_TYPE_CODE = "code";
    
    // JSON change types
    public static final String JSON_CHANGE_TYPE_CHANGED = "TYPE_CHANGED";
    public static final String JSON_CHANGE_VALUE_CHANGED = "VALUE_CHANGED";
    public static final String JSON_CHANGE_FIELD_REMOVED = "FIELD_REMOVED";
    public static final String JSON_CHANGE_FIELD_ADDED = "FIELD_ADDED";
    public static final String JSON_CHANGE_ARRAY_ELEMENT_REMOVED = "ARRAY_ELEMENT_REMOVED";
    public static final String JSON_CHANGE_ARRAY_ELEMENT_ADDED = "ARRAY_ELEMENT_ADDED";
    
    // Code statistics keys
    public static final String KEY_LINES_OF_CODE_1 = "linesOfCode1";
    public static final String KEY_LINES_OF_CODE_2 = "linesOfCode2";
    public static final String KEY_COMMENT_LINES_1 = "commentLines1";
    public static final String KEY_COMMENT_LINES_2 = "commentLines2";
} 