package com.devtoolkit.tools.cron.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Cron tool
 */
@Configuration
public class CronConfig {
    
    // Cron tool specific configuration can be added here
    // For example: default execution count, date format, etc.
    
    public static final int DEFAULT_EXECUTION_COUNT = 5;
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
} 