package com.devtoolkit.tools.regextester.dto;

import lombok.Data;

/**
 * Response DTO for Regex Tester operations
 */
@Data
public class RegexTesterResponse {
    private String pattern;
    private String text;
    private boolean matches;
    private String result;
    private boolean success;
    private String message;
} 