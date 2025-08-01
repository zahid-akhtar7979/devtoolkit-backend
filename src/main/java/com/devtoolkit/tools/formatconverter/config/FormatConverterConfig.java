package com.devtoolkit.tools.formatconverter.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Format Converter tool
 */
@Configuration
public class FormatConverterConfig {
    
    public static final String[] SUPPORTED_FORMATS = {"JSON", "YAML", "XML"};
    public static final String DEFAULT_ENCODING = "UTF-8";
} 