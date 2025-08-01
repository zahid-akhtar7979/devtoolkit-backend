package com.devtoolkit.tools.uuidgenerator.constants;

/**
 * Constants for UUID Generator operations
 */
public final class UuidGeneratorConstants {
    
    private UuidGeneratorConstants() {
        // Private constructor to prevent instantiation
    }
    
    // UUID Types
    public static final String UUID_V1 = "v1";
    public static final String UUID_V4 = "v4";
    public static final String UUID_V5 = "v5";
    
    // Default values
    public static final String DEFAULT_TYPE = "v4";
    public static final int MAX_COUNT = 1000;
    
    // Messages
    public static final String SINGLE_UUID_SUCCESS_MESSAGE = "UUID generated successfully";
    public static final String MULTIPLE_UUIDS_SUCCESS_MESSAGE = "Multiple UUIDs generated successfully";
    public static final String INVALID_COUNT_MESSAGE = "Count must be greater than 0 and less than or equal to 1000";
    public static final String INVALID_TYPE_MESSAGE = "Invalid UUID type. Supported types: v1, v4, v5";
    
    // Error messages
    public static final String COUNT_MUST_BE_GREATER_THAN_ZERO = "Count must be greater than 0";
    public static final String COUNT_CANNOT_EXCEED_PREFIX = "Count cannot exceed ";
    public static final String INVALID_UUID_TYPE_PREFIX = "Invalid UUID type. Supported types: ";
    public static final String FAILED_TO_GENERATE_UUID = "Failed to generate UUID";
    public static final String FAILED_TO_GENERATE_MULTIPLE_UUIDS = "Failed to generate multiple UUIDs";
} 