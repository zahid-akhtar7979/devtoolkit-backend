package com.devtoolkit.tools.curlgenerator.dto;

import lombok.Data;

/**
 * Request DTO for cURL Generator operations
 */
@Data
public class CurlGeneratorRequest {
    private String url;
    private String method; // GET, POST, PUT, DELETE, etc.
    private String headers; // Optional headers
    private String body; // Optional request body
} 