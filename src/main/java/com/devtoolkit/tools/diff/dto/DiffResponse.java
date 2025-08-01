package com.devtoolkit.tools.diff.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Response DTO for Diff operations
 */
@Data
public class DiffResponse {
    private String text1;
    private String text2;
    private List<String> differences;
    private Map<String, Object> enhancedDiff;
    private boolean hasDifferences;
    private String message;
} 