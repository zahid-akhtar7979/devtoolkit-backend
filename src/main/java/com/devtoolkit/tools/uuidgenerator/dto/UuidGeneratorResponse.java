package com.devtoolkit.tools.uuidgenerator.dto;

import lombok.Data;
import java.util.List;

/**
 * Response DTO for UUID Generator operations
 */
@Data
public class UuidGeneratorResponse {
    private String type;
    private String uuid; // Single UUID
    private List<String> uuids; // Multiple UUIDs
    private Integer count;
    private boolean success;
    private String message;
} 