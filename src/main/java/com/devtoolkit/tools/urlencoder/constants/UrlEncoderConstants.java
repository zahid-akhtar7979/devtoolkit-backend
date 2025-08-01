package com.devtoolkit.tools.urlencoder.constants;

/**
 * Constants for URLEncoder operations
 */
public final class UrlEncoderConstants {
    
    private UrlEncoderConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Operations
    public static final String ENCODE_OPERATION = "encode";
    public static final String DECODE_OPERATION = "decode";
    
    // Error messages
    public static final String EMPTY_TEXT_MESSAGE = "Text cannot be null or empty";
    public static final String INVALID_OPERATION_MESSAGE = "Invalid operation. Must be 'encode' or 'decode'";
    public static final String ENCODE_SUCCESS_MESSAGE = "Text encoded successfully";
    public static final String DECODE_SUCCESS_MESSAGE = "Text decoded successfully";
    public static final String ENCODE_ERROR_MESSAGE = "Failed to encode text";
    public static final String DECODE_ERROR_MESSAGE = "Failed to decode text";
    public static final String INVALID_ENCODING_MESSAGE = "Invalid encoding: ";
    
    // Default values
    public static final String DEFAULT_ENCODING = "UTF-8";
} 