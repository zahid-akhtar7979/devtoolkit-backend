package com.devtoolkit.tools.base64.constants;

/**
 * Constants for Base64 operations
 */
public final class Base64Constants {
    
    private Base64Constants() {
        // Private constructor to prevent instantiation
    }
    
    // Operations
    public static final String ENCODE_OPERATION = "encode";
    public static final String DECODE_OPERATION = "decode";
    
    // Error messages
    public static final String EMPTY_TEXT_MESSAGE = "Text cannot be null or empty";
    public static final String ENCODE_SUCCESS_MESSAGE = "Text encoded successfully";
    public static final String DECODE_SUCCESS_MESSAGE = "Text decoded successfully";
    public static final String ENCODE_ERROR_MESSAGE = "Failed to encode text";
    public static final String DECODE_ERROR_MESSAGE = "Failed to decode text - invalid Base64 format";
    
    // Default values
    public static final String DEFAULT_ENCODING = "UTF-8";
} 