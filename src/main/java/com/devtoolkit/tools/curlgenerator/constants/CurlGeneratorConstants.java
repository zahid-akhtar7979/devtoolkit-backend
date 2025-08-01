package com.devtoolkit.tools.curlgenerator.constants;

/**
 * Constants for cURL Generator operations
 */
public final class CurlGeneratorConstants {
    
    private CurlGeneratorConstants() {
        // Private constructor to prevent instantiation
    }
    
    // HTTP Methods
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";
    public static final String PATCH_METHOD = "PATCH";
    
    // cURL command
    public static final String CURL_COMMAND = "curl";
    
    // Messages
    public static final String GENERATION_SUCCESS_MESSAGE = "cURL command generated successfully";
    public static final String EMPTY_URL_MESSAGE = "URL cannot be null or empty";
    public static final String INVALID_URL_MESSAGE = "Invalid URL format";
    
    // Error messages
    public static final String GENERATION_FAILED_MESSAGE = "Failed to generate cURL command";
} 