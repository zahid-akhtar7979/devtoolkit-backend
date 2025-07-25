package com.devtoolkit.diff.dto;

import lombok.Data;

@Data
public class DiffRequest {
    private String text1;
    private String text2;
    private boolean ignoreCase = false;
    private boolean ignoreWhitespace = false;
    private boolean ignoreLineEndings = false;
    private int contextLines = 3;
    private DiffType diffType = DiffType.TEXT;
    
    public enum DiffType {
        TEXT,           // Regular text diff
        JSON,           // JSON structural diff
        XML,            // XML structural diff
        CODE            // Code-aware diff
    }
} 