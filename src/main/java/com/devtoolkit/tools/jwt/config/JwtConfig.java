package com.devtoolkit.tools.jwt.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for JWT tool
 */
@Configuration
public class JwtConfig {
    
    // JWT tool specific configuration can be added here
    // For example: default algorithms, token expiration settings, etc.
    
    public static final String DEFAULT_ALGORITHM = "HS256";
    public static final int DEFAULT_TOKEN_EXPIRATION_HOURS = 24;
} 