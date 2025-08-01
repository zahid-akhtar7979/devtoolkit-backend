package com.devtoolkit.tools.imagetopdf.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ImageToPdf tool
 */
@Configuration
public class ImageToPdfConfig {
    
    public static final String DEFAULT_OUTPUT_FILENAME = "images-to-pdf.pdf";
    public static final String DEFAULT_PAGE_SIZE = "A4";
    public static final int DEFAULT_MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final int DEFAULT_MAX_IMAGES = 50;
    public static final String[] SUPPORTED_FORMATS = {"jpg", "jpeg", "png", "gif", "bmp", "tiff"};
    public static final String[] SUPPORTED_PAGE_SIZES = {"A4", "Letter", "auto"};
} 