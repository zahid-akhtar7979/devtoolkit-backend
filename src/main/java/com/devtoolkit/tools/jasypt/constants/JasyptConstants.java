package com.devtoolkit.tools.jasypt.constants;

/**
 * Constants for Jasypt operations
 */
public final class JasyptConstants {
    
    private JasyptConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Operations
    public static final String ENCRYPT_OPERATION = "encrypt";
    public static final String DECRYPT_OPERATION = "decrypt";
    
    // Algorithms
    public static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDES";
    public static final String PBEWITHMD5ANDDES = "PBEWithMD5AndDES";
    public static final String PBEWITHMD5ANDTRIPLEDES = "PBEWithMD5AndTripleDES";
    public static final String PBEWITHSHA1ANDDESEDE = "PBEWithSHA1AndDESede";
    public static final String PBEWITHSHA1ANDRC2_40 = "PBEWithSHA1AndRC2_40";
    
    // Error messages
    public static final String EMPTY_TEXT_MESSAGE = "Text cannot be null or empty";
    public static final String EMPTY_PASSWORD_MESSAGE = "Password cannot be null or empty";
    public static final String INVALID_ALGORITHM_MESSAGE = "Invalid encryption algorithm";
    public static final String ENCRYPT_SUCCESS_MESSAGE = "Text encrypted successfully";
    public static final String DECRYPT_SUCCESS_MESSAGE = "Text decrypted successfully";
    
    // Default values
    public static final String DEFAULT_ENCODING = "UTF-8";
    
    // Jasypt configuration
    public static final String PROVIDER_NAME = "SunJCE";
    public static final String SALT_GENERATOR_CLASS = "org.jasypt.salt.RandomSaltGenerator";
    public static final String STRING_OUTPUT_TYPE = "base64";
    
    // Error messages
    public static final String ENCRYPTION_FAILED_MESSAGE = "Encryption failed";
    public static final String DECRYPTION_FAILED_MESSAGE = "Decryption failed";
} 