package com.devtoolkit.tools.uuidgenerator.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for UUID Generator tool
 */
@Configuration
public class UuidGeneratorConfig {
    
    public static final String DEFAULT_TYPE = "v4";
    public static final int MAX_COUNT = 1000;
    public static final String[] SUPPORTED_TYPES = {"v1", "v4", "v5"};
} 