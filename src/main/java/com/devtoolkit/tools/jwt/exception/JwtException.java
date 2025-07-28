package com.devtoolkit.tools.jwt.exception;

public class JwtException extends RuntimeException {
    private final String errorCode;
    private final String userMessage;

    public JwtException(String errorCode, String userMessage, String technicalMessage) {
        super(technicalMessage);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }

    public JwtException(String errorCode, String userMessage, String technicalMessage, Throwable cause) {
        super(technicalMessage, cause);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    // Predefined JWT error types
    public static class InvalidFormat extends JwtException {
        public InvalidFormat(String details) {
            super("INVALID_FORMAT", 
                "The JWT token format is invalid. Please ensure you have a valid JWT token with header.payload.signature format.", 
                "Invalid JWT format: " + details);
        }
    }

    public static class InvalidEncoding extends JwtException {
        public InvalidEncoding(String details) {
            super("INVALID_ENCODING", 
                "The JWT token contains invalid Base64 encoding. Please check if the token was copied correctly.", 
                "Invalid Base64 encoding: " + details);
        }
    }

    public static class InvalidJson extends JwtException {
        public InvalidJson(String details) {
            super("INVALID_JSON", 
                "The JWT token contains invalid JSON data. The token appears to be corrupted or malformed.", 
                "Invalid JSON in JWT: " + details);
        }
    }

    public static class EmptyToken extends JwtException {
        public EmptyToken() {
            super("EMPTY_TOKEN", 
                "Please provide a JWT token to decode or verify.", 
                "Token cannot be null or empty");
        }
    }

    public static class EmptySecret extends JwtException {
        public EmptySecret() {
            super("EMPTY_SECRET", 
                "Please provide a secret key to verify the JWT token.", 
                "Secret cannot be null or empty for verification");
        }
    }

    public static class VerificationFailed extends JwtException {
        public VerificationFailed(String details) {
            super("VERIFICATION_FAILED", 
                "JWT token verification failed. The token may be expired, tampered with, or signed with a different secret.", 
                "JWT verification failed: " + details);
        }
    }

    public static class MalformedToken extends JwtException {
        public MalformedToken(String details) {
            super("MALFORMED_TOKEN", 
                "The JWT token is malformed or corrupted. Please check if you have the complete token.", 
                "Malformed JWT token: " + details);
        }
    }
} 