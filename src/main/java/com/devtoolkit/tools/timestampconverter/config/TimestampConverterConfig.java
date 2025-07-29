package com.devtoolkit.tools.timestampconverter.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Timestamp Converter tool
 */
@Configuration
public class TimestampConverterConfig {
    
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String[] SUPPORTED_FORMATS = {
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd",
        "dd/MM/yyyy",
        "MM/dd/yyyy",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd'T'HH:mm:ss.SSS"
    };
} 