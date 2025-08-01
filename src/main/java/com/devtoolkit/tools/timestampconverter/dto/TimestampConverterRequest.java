package com.devtoolkit.tools.timestampconverter.dto;

import lombok.Data;

/**
 * Request DTO for Timestamp Converter operations
 */
@Data
public class TimestampConverterRequest {
    private String timestamp; // Unix timestamp
    private String format; // Output date format (optional)
} 