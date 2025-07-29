package com.devtoolkit.tools.urlencoder.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for URLEncoder tool
 */
@Configuration
public class UrlEncoderConfig {
    
    // URLEncoder tool specific configuration can be added here
    // For example: default encoding, supported encodings, etc.
    
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String[] SUPPORTED_ENCODINGS = {"UTF-8", "ISO-8859-1", "US-ASCII"};
} 