package com.devtoolkit.tools.timestampconverter.dto;

import lombok.Data;

/**
 * Response DTO for Timestamp Converter operations
 */
@Data
public class TimestampConverterResponse {
    private String originalTimestamp;
    private String convertedDate;
    private String format;
    private Long timestampValue;
    private boolean success;
    private String message;
} 