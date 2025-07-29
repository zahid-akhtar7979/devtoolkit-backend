package com.devtoolkit.tools.hash.constants;

/**
 * Constants for Hash operations
 */
public final class HashConstants {
    
    private HashConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Supported algorithms
    public static final String MD5_ALGORITHM = "MD5";
    public static final String SHA1_ALGORITHM = "SHA-1";
    public static final String SHA256_ALGORITHM = "SHA-256";
    public static final String SHA512_ALGORITHM = "SHA-512";
    
    // Error messages
    public static final String EMPTY_TEXT_MESSAGE = "Text cannot be null or empty";
    public static final String INVALID_ALGORITHM_MESSAGE = "Unsupported hash algorithm";
    public static final String HASH_SUCCESS_MESSAGE = "Hash generated successfully";
    public static final String MULTI_HASH_SUCCESS_MESSAGE = "Multiple hashes generated successfully";
    
    // Default values
    public static final String DEFAULT_ENCODING = "UTF-8";
} 