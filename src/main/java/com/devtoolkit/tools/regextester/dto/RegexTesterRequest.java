package com.devtoolkit.tools.regextester.dto;

import lombok.Data;

/**
 * Request DTO for Regex Tester operations
 */
@Data
public class RegexTesterRequest {
    private String pattern; // Regular expression pattern
    private String text; // Text to test against the pattern
} 