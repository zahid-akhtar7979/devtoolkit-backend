package com.devtoolkit.tools.base64.dto;

import lombok.Data;

/**
 * Response DTO for Base64 operations
 */
@Data
public class Base64Response {
    private String originalText;
    private String processedText;
    private String operation;
    private boolean success;
    private String message;
} 