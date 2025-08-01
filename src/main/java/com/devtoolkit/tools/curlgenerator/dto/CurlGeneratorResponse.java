package com.devtoolkit.tools.curlgenerator.dto;

import lombok.Data;

/**
 * Response DTO for cURL Generator operations
 */
@Data
public class CurlGeneratorResponse {
    private String url;
    private String method;
    private String headers;
    private String body;
    private String curlCommand;
    private boolean success;
    private String message;
} 