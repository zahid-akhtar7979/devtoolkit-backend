package com.devtoolkit.tools.jasypt.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Jasypt tool
 */
@Configuration
public class JasyptConfig {
    
    // Jasypt tool specific configuration can be added here
    // For example: default algorithms, key derivation, etc.
    
    public static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDES";
    public static final String DEFAULT_ENCODING = "UTF-8";
} 