package com.devtoolkit.tools.curlgenerator.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for cURL Generator tool
 */
@Configuration
public class CurlGeneratorConfig {
    
    public static final String[] SUPPORTED_METHODS = {"GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"};
    public static final String DEFAULT_METHOD = "GET";
} 