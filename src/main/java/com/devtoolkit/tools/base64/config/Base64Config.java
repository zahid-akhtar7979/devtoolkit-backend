package com.devtoolkit.tools.base64.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Base64 tool
 */
@Configuration
public class Base64Config {
    
    // Base64 tool specific configuration can be added here
    // For example: default encoding, chunk size, etc.
    
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final int DEFAULT_CHUNK_SIZE = 8192;
} 