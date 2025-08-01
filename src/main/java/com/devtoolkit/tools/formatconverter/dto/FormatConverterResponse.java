package com.devtoolkit.tools.formatconverter.dto;

import lombok.Data;

/**
 * Response DTO for Format Converter operations
 */
@Data
public class FormatConverterResponse {
    private String originalText;
    private String convertedText;
    private String sourceFormat;
    private String targetFormat;
    private boolean success;
    private String message;
} 