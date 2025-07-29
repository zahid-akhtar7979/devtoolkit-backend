package com.devtoolkit.tools.formatconverter.service;

import com.devtoolkit.tools.formatconverter.dto.FormatConverterResponse;

/**
 * Service interface for Format Converter operations
 */
public interface FormatConverterService {
    
    /**
     * Convert text between different formats
     * 
     * @param text the text to convert
     * @param sourceFormat the source format (JSON, YAML, XML)
     * @param targetFormat the target format (JSON, YAML, XML)
     * @return FormatConverterResponse with the converted text
     */
    FormatConverterResponse convertFormat(String text, String sourceFormat, String targetFormat);
} 