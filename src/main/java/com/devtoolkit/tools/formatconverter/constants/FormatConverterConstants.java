package com.devtoolkit.tools.formatconverter.constants;

/**
 * Constants for Format Converter operations
 */
public final class FormatConverterConstants {
    
    private FormatConverterConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Supported formats
    public static final String JSON_FORMAT = "JSON";
    public static final String YAML_FORMAT = "YAML";
    public static final String XML_FORMAT = "XML";
    
    // Messages
    public static final String CONVERSION_SUCCESS_MESSAGE = "Format conversion completed successfully";
    public static final String EMPTY_TEXT_MESSAGE = "Text cannot be null or empty";
    public static final String INVALID_FORMAT_MESSAGE = "Invalid format. Supported formats: JSON, YAML, XML";
    public static final String UNSUPPORTED_CONVERSION_MESSAGE = "Conversion from {0} to {1} is not supported";
    
    // XML formatting
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    
    // Error messages
    public static final String CONVERSION_FAILED_MESSAGE = "Failed to convert format";
    
    // Unsupported conversion message parts
    public static final String UNSUPPORTED_CONVERSION_MESSAGE_PREFIX = "Conversion from ";
    public static final String UNSUPPORTED_CONVERSION_MESSAGE_MIDDLE = " to ";
    public static final String UNSUPPORTED_CONVERSION_MESSAGE_SUFFIX = " is not supported";
} 