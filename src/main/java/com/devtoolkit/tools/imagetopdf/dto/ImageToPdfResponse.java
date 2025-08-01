package com.devtoolkit.tools.imagetopdf.dto;

import lombok.Data;

/**
 * Response DTO for ImageToPdf operations
 */
@Data
public class ImageToPdfResponse {
    private String pdfContent; // Base64 encoded PDF content
    private String fileName;
    private Long fileSize;
    private Integer totalPages;
    private Integer imagesProcessed;
    private String pageSize;
    private boolean success;
    private String message;
} 