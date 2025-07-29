package com.devtoolkit.tools.uuidgenerator.dto;

import lombok.Data;

/**
 * Request DTO for UUID Generator operations
 */
@Data
public class UuidGeneratorRequest {
    private String type; // v1, v4, v5
    private Integer count; // Optional, for generating multiple UUIDs
} 