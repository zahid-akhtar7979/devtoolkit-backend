package com.devtoolkit.tools.formatconverter.dto;

import lombok.Data;

/**
 * Request DTO for Format Converter operations
 */
@Data
public class FormatConverterRequest {
    private String text;
    private String sourceFormat; // JSON, YAML, XML
    private String targetFormat; // JSON, YAML, XML
} 