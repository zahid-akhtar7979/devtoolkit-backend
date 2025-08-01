package com.devtoolkit.tools.urlencoder.dto;

import lombok.Data;

/**
 * Request DTO for URLEncoder operations
 */
@Data
public class UrlEncoderRequest {
    private String text;
    private String operation; // "encode" or "decode"
    private String encoding; // Optional, defaults to UTF-8
} 