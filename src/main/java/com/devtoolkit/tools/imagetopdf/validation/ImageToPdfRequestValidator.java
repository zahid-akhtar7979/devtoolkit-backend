package com.devtoolkit.tools.imagetopdf.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.imagetopdf.constants.ImageToPdfConstants;
import com.devtoolkit.tools.imagetopdf.dto.ImageToPdfRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * Validator for ImageToPdf requests
 */
@Component
public class ImageToPdfRequestValidator {
    
    /**
     * Validates ImageToPdf request metadata
     * 
     * @param request the ImageToPdf request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(ImageToPdfRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        // Validate page size if provided
        if (request.getPageSize() != null && !isValidPageSize(request.getPageSize())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                "Invalid page size. Supported sizes: A4, Letter, auto");
        }
        
        // Validate max file size if provided
        if (request.getMaxFileSize() != null && request.getMaxFileSize() <= 0) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Max file size must be greater than 0");
        }
    }
    
    /**
     * Validates uploaded image files
     * 
     * @param images the uploaded image files
     * @param maxFileSize maximum allowed file size
     * @param maxImages maximum allowed number of images
     * @throws DevToolkitException if validation fails
     */
    public void validateImageFiles(List<MultipartFile> images, int maxFileSize, int maxImages) {
        if (images == null || images.isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, ImageToPdfConstants.NO_IMAGES_MESSAGE);
        }
        
        if (images.size() > maxImages) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                ImageToPdfConstants.TOO_MANY_IMAGES_MESSAGE + maxImages);
        }
        
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                continue; // Skip empty files
            }
            
            // Validate file size
            if (image.getSize() > maxFileSize) {
                throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                    ImageToPdfConstants.FILE_TOO_LARGE_MESSAGE);
            }
            
            // Validate file format
            String originalFilename = image.getOriginalFilename();
            if (originalFilename != null && !isValidImageFormat(originalFilename)) {
                throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                    ImageToPdfConstants.INVALID_FILE_FORMAT_MESSAGE + 
                    String.join(", ", ImageToPdfConstants.SUPPORTED_FORMATS));
            }
        }
    }
    
    private boolean isValidPageSize(String pageSize) {
        return ImageToPdfConstants.PAGE_SIZE_A4.equalsIgnoreCase(pageSize) ||
               ImageToPdfConstants.PAGE_SIZE_LETTER.equalsIgnoreCase(pageSize) ||
               ImageToPdfConstants.PAGE_SIZE_AUTO.equalsIgnoreCase(pageSize);
    }
    
    private boolean isValidImageFormat(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return Arrays.asList(ImageToPdfConstants.SUPPORTED_FORMATS).contains(extension);
    }
    
    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex > 0 ? filename.substring(lastDotIndex + 1) : "";
    }
} 