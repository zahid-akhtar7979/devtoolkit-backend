package com.devtoolkit.tools.jwt.dto;

import lombok.Data;
import java.util.Map;

/**
 * Response DTO for JWT operations
 */
@Data
public class JwtResponse {
    private Map<String, Object> header;
    private Map<String, Object> payload;
    private String signature;
    private boolean valid;
    private String message;
} 