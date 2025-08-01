package com.devtoolkit.tools.hash.dto;

import lombok.Data;
import java.util.Map;

/**
 * Response DTO for Hash operations
 */
@Data
public class HashResponse {
    private String originalText;
    private Map<String, String> hashes;
    private String specificHash;
    private String algorithm;
    private boolean success;
    private String message;
} 