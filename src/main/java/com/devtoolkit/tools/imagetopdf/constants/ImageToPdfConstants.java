package com.devtoolkit.tools.imagetopdf.constants;

/**
 * Constants for ImageToPdf operations
 */
public final class ImageToPdfConstants {
    
    private ImageToPdfConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Page sizes
    public static final String PAGE_SIZE_A4 = "A4";
    public static final String PAGE_SIZE_LETTER = "Letter";
    public static final String PAGE_SIZE_AUTO = "auto";
    
    // Default values
    public static final String DEFAULT_OUTPUT_FILENAME = "images-to-pdf.pdf";
    public static final String DEFAULT_PAGE_SIZE = PAGE_SIZE_A4;
    public static final int DEFAULT_MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final int DEFAULT_MAX_IMAGES = 50;
    
    // Supported image formats
    public static final String[] SUPPORTED_FORMATS = {"jpg", "jpeg", "png", "gif", "bmp", "tiff"};
    
    // Messages
    public static final String CONVERSION_SUCCESS_MESSAGE = "Successfully converted images to PDF";
    public static final String NO_IMAGES_MESSAGE = "No images provided";
    public static final String NO_VALID_IMAGES_MESSAGE = "No valid images could be processed";
    public static final String INVALID_FILE_FORMAT_MESSAGE = "Invalid file format. Supported formats: ";
    public static final String FILE_TOO_LARGE_MESSAGE = "File size exceeds maximum allowed size";
    public static final String TOO_MANY_IMAGES_MESSAGE = "Too many images. Maximum allowed: ";
} 