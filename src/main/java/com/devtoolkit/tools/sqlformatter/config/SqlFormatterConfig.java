package com.devtoolkit.tools.sqlformatter.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for SQL Formatter tool
 */
@Configuration
public class SqlFormatterConfig {
    
    public static final String DEFAULT_DIALECT = "standard";
    public static final String[] SUPPORTED_DIALECTS = {"mysql", "postgresql", "oracle", "sqlserver", "sqlite", "standard"};
} 