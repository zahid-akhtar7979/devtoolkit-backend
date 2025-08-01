package com.devtoolkit.tools.urlencoder.dto;

import lombok.Data;

/**
 * Response DTO for URLEncoder operations
 */
@Data
public class UrlEncoderResponse {
    private String originalText;
    private String processedText;
    private String operation;
    private String encoding;
    private boolean success;
    private String message;
} 