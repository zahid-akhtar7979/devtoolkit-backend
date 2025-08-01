package com.devtoolkit.tools.imagetopdf;

import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.imagetopdf.api.IImageToPdfResources;
import com.devtoolkit.tools.imagetopdf.constants.ImageToPdfConstants;
import com.devtoolkit.tools.imagetopdf.dto.ImageToPdfResponse;
import com.devtoolkit.tools.imagetopdf.service.ImageToPdfService;
import com.devtoolkit.tools.imagetopdf.validation.ImageToPdfRequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller for ImageToPdf operations
 * 
 * <p>This controller handles all input validation before delegating to the service layer.
 * It validates file uploads, page sizes, and other parameters to ensure data integrity.</p>
 * 
 * @author DevToolkit
 * @version 1.0
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ImageToPdfController implements IImageToPdfResources {
    
    @Autowired
    private ImageToPdfService imageToPdfService;
    
    @Autowired
    private ImageToPdfRequestValidator validator;
    
    @Override
    public ServiceResponse<ImageToPdfResponse> convertImagesToPdf(
            List<MultipartFile> images,
            String outputFileName,
            String pageSize) {
        
        log.info("Received PDF conversion request for {} images", images != null ? images.size() : 0);
        
        // Validate uploaded files
        validator.validateImageFiles(images, ImageToPdfConstants.DEFAULT_MAX_FILE_SIZE, ImageToPdfConstants.DEFAULT_MAX_IMAGES);
        
        // Validate page size if provided
        validatePageSize(pageSize);
        
        // Validate output filename if provided
        validateOutputFileName(outputFileName);
        
        log.info("Validation passed, proceeding with PDF conversion");
        
        // Convert images to PDF (service assumes all validation is complete)
        ImageToPdfResponse result = imageToPdfService.convertImagesToPdf(images, outputFileName, pageSize);
        
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
    
    /**
     * Validates the page size parameter
     * 
     * @param pageSize the page size to validate
     */
    private void validatePageSize(String pageSize) {
        if (StringUtils.hasText(pageSize)) {
            String normalizedPageSize = pageSize.trim();
            if (!isValidPageSize(normalizedPageSize)) {
                throw new com.devtoolkit.exception.DevToolkitException(
                    com.devtoolkit.common.enums.ErrorCode.VALIDATION_ERROR,
                    "Invalid page size. Supported sizes: A4, Letter, auto"
                );
            }
        }
    }
    
    /**
     * Validates the output filename parameter
     * 
     * @param outputFileName the output filename to validate
     */
    private void validateOutputFileName(String outputFileName) {
        if (StringUtils.hasText(outputFileName)) {
            String normalizedFileName = outputFileName.trim();
            if (normalizedFileName.length() > 255) {
                throw new com.devtoolkit.exception.DevToolkitException(
                    com.devtoolkit.common.enums.ErrorCode.VALIDATION_ERROR,
                    "Output filename is too long. Maximum length is 255 characters"
                );
            }
            if (!normalizedFileName.toLowerCase().endsWith(".pdf")) {
                throw new com.devtoolkit.exception.DevToolkitException(
                    com.devtoolkit.common.enums.ErrorCode.VALIDATION_ERROR,
                    "Output filename must end with .pdf extension"
                );
            }
        }
    }
    
    /**
     * Checks if the provided page size is valid
     * 
     * @param pageSize the page size to check
     * @return true if valid, false otherwise
     */
    private boolean isValidPageSize(String pageSize) {
        return ImageToPdfConstants.PAGE_SIZE_A4.equalsIgnoreCase(pageSize) ||
               ImageToPdfConstants.PAGE_SIZE_LETTER.equalsIgnoreCase(pageSize) ||
               ImageToPdfConstants.PAGE_SIZE_AUTO.equalsIgnoreCase(pageSize);
    }
}
