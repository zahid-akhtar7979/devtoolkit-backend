package com.devtoolkit.tools.timestampconverter.constants;

/**
 * Constants for Timestamp Converter operations
 */
public final class TimestampConverterConstants {
    
    private TimestampConverterConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Default values
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // Messages
    public static final String CONVERSION_SUCCESS_MESSAGE = "Timestamp converted successfully";
    public static final String EMPTY_TIMESTAMP_MESSAGE = "Timestamp cannot be null or empty";
    public static final String INVALID_TIMESTAMP_MESSAGE = "Invalid timestamp format";
    public static final String INVALID_FORMAT_MESSAGE = "Invalid date format";
    
    // Error messages
    public static final String CONVERSION_FAILED_MESSAGE = "Failed to convert timestamp";
} 