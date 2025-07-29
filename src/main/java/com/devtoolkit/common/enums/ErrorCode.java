package com.devtoolkit.common.enums;

/**
 * Enum to define error codes for the application
 */
public enum ErrorCode {
    
    // General errors
    INTERNAL_EXCEPTION("DTK-500", "Internal server error"),
    VALIDATION_ERROR("DTK-400", "Validation error"),
    UNAUTHORIZED("DTK-401", "Unauthorized access"),
    FORBIDDEN("DTK-403", "Access forbidden"),
    NOT_FOUND("DTK-404", "Resource not found"),
    
    // Base64 tool errors
    BASE64_DECODE_ERROR("DTK-1001", "Failed to decode base64 string"),
    BASE64_ENCODE_ERROR("DTK-1002", "Failed to encode to base64"),
    BASE64_INVALID_INPUT("DTK-1003", "Invalid input for base64 operation"),
    
    // Cron tool errors
    CRON_INVALID_EXPRESSION("DTK-2001", "Invalid cron expression"),
    CRON_PARSE_ERROR("DTK-2002", "Failed to parse cron expression"),
    
    // Diff tool errors
    DIFF_INVALID_INPUT("DTK-3001", "Invalid input for diff operation"),
    DIFF_PROCESSING_ERROR("DTK-3002", "Error processing diff operation"),
    
    // Hash tool errors
    HASH_INVALID_ALGORITHM("DTK-4001", "Invalid hash algorithm"),
    HASH_PROCESSING_ERROR("DTK-4002", "Error processing hash operation"),
    
    // Jasypt tool errors
    JASYPT_ENCRYPTION_ERROR("DTK-5001", "Encryption failed"),
    JASYPT_DECRYPTION_ERROR("DTK-5002", "Decryption failed"),
    JASYPT_INVALID_KEY("DTK-5003", "Invalid encryption key"),
    
    // JWT tool errors
    JWT_GENERATION_ERROR("DTK-6001", "Failed to generate JWT token"),
    JWT_VALIDATION_ERROR("DTK-6002", "Failed to validate JWT token"),
    JWT_INVALID_SECRET("DTK-6003", "Invalid JWT secret"),
    JWT_TOKEN_EXPIRED("DTK-6004", "JWT token has expired"),
    JWT_UNSUPPORTED_FORMAT("DTK-6005", "Unsupported JWT token format"),
    JWT_MALFORMED_TOKEN("DTK-6006", "Malformed JWT token"),
    JWT_INVALID_SIGNATURE("DTK-6007", "Invalid JWT signature"),
    JWT_INVALID_FORMAT("DTK-6008", "Invalid JWT token format"),
    
    // Utility tool errors
    UTILITY_PROCESSING_ERROR("DTK-7001", "Error processing utility operation"),
    
    // Image to PDF errors
    IMAGE_TO_PDF_CONVERSION_ERROR("DTK-8001", "Failed to convert image to PDF"),
    IMAGE_TO_PDF_INVALID_FORMAT("DTK-8002", "Invalid image format"),
    IMAGE_TO_PDF_FILE_TOO_LARGE("DTK-8003", "Image file too large");
    
    private final String code;
    private final String defaultMessage;
    
    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDefaultMessage() {
        return defaultMessage;
    }
} 