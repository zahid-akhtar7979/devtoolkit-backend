package com.devtoolkit.tools.imagetopdf.dto;

import lombok.Data;

/**
 * Request DTO for ImageToPdf operations
 * Note: Image files are handled via MultipartFile in the controller
 */
@Data
public class ImageToPdfRequest {
    private String outputFileName; // Optional, defaults to "images-to-pdf.pdf"
    private String pageSize; // Optional, defaults to "A4" (A4, Letter, auto)
    private Integer maxFileSize; // Optional, max file size in bytes
    private String[] allowedFormats; // Optional, allowed image formats
} 