package com.devtoolkit.tools.diff.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Diff tool
 */
@Configuration
public class DiffConfig {
    
    // Diff tool specific configuration can be added here
    // For example: default context lines, encoding, etc.
    
    public static final int DEFAULT_CONTEXT_LINES = 3;
    public static final String DEFAULT_ENCODING = "UTF-8";
} 