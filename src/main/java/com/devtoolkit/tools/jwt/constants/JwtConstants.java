package com.devtoolkit.tools.jwt.constants;

/**
 * Constants for JWT operations
 */
public final class JwtConstants {
    
    private JwtConstants() {
        // Private constructor to prevent instantiation
    }
    
    // JWT Token parts
    public static final String HEADER_PART = "header";
    public static final String PAYLOAD_PART = "payload";
    public static final String SIGNATURE_PART = "signature";
    
    // JWT Token format
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String TOKEN_SEPARATOR = "\\.";
    public static final int EXPECTED_TOKEN_PARTS = 3;
    
    // Error messages
    public static final String EMPTY_TOKEN_MESSAGE = "Token cannot be null or empty";
    public static final String EMPTY_SECRET_MESSAGE = "Secret cannot be null or empty for verification";
    public static final String INVALID_FORMAT_MESSAGE = "Invalid JWT token format";
    public static final String INVALID_ENCODING_MESSAGE = "Invalid Base64 encoding";
    public static final String INVALID_JSON_MESSAGE = "Invalid JSON in JWT";
    
    // Default values
    public static final String DEFAULT_ENCODING = "UTF-8";
    
    // Success messages
    public static final String TOKEN_DECODED_SUCCESS_MESSAGE = "Token decoded successfully";
    
    // Error messages for specific scenarios
    public static final String INVALID_BASE64_ENCODING_IN_SIGNATURE = "Invalid Base64 encoding in signature";
    public static final String UNEXPECTED_ERROR_DURING_TOKEN_DECODING = "Unexpected error during token decoding";
    public static final String UNEXPECTED_ERROR_DURING_VERIFICATION = "Unexpected error during verification";
    public static final String TOKEN_HAS_PARTS_EXPECTED_MESSAGE = "Token has ";
    public static final String PARTS_EXPECTED_3_MESSAGE = " parts, expected 3 (header.payload.signature)";
    public static final String EMPTY_PART_MESSAGE = "Empty ";
    public static final String PART_MESSAGE = " part";
    public static final String INVALID_BASE64_ENCODING_IN_MESSAGE = "Invalid Base64 encoding in ";
    public static final String INVALID_JSON_IN_MESSAGE = "Invalid JSON in ";
    public static final String JWT_TOKEN_EXPIRED_MESSAGE = "JWT token has expired";
    public static final String UNSUPPORTED_JWT_TOKEN_FORMAT_MESSAGE = "Unsupported JWT token format";
    public static final String MALFORMED_JWT_TOKEN_MESSAGE = "Malformed JWT token";
    public static final String INVALID_JWT_SIGNATURE_MESSAGE = "Invalid JWT signature - the token may have been tampered with or signed with a different secret";
    public static final String INVALID_JWT_TOKEN_MESSAGE = "Invalid JWT token";
} 