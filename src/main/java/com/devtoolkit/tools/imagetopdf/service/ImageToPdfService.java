package com.devtoolkit.tools.imagetopdf.service;

import com.devtoolkit.tools.imagetopdf.dto.ImageToPdfResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for ImageToPdf operations
 */
public interface ImageToPdfService {
    
    /**
     * Convert images to PDF
     * 
     * @param images the image files to convert
     * @param outputFileName the output PDF filename
     * @param pageSize the page size (A4, Letter, auto)
     * @return ImageToPdfResponse with the generated PDF
     */
    ImageToPdfResponse convertImagesToPdf(List<MultipartFile> images, String outputFileName, String pageSize);
} 