package com.devtoolkit.tools.jasypt.dto;

import lombok.Data;

/**
 * Response DTO for Jasypt operations
 */
@Data
public class JasyptResponse {
    private String originalText;
    private String processedText;
    private String password;
    private String algorithm;
    private String operation;
    private boolean success;
    private String message;
} 