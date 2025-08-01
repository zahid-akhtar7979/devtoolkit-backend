package com.devtoolkit.tools.hash.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Hash tool
 */
@Configuration
public class HashConfig {
    
    // Hash tool specific configuration can be added here
    // For example: default algorithms, encoding, etc.
    
    public static final String DEFAULT_ALGORITHM = "SHA-256";
    public static final String DEFAULT_ENCODING = "UTF-8";
} 