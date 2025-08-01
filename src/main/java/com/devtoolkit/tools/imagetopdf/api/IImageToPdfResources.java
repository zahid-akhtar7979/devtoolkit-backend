package com.devtoolkit.tools.imagetopdf.api;

import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.imagetopdf.dto.ImageToPdfResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * API interface for ImageToPdf operations
 * Note: This tool uses MultipartFile for file uploads instead of ServiceRequest
 */
@RequestMapping("/api/imagetopdf")
public interface IImageToPdfResources {
    
    /**
     * Convert images to PDF
     * 
     * @param images the image files to convert
     * @param outputFileName the output PDF filename (optional)
     * @param pageSize the page size (optional: A4, Letter, auto)
     * @return ServiceResponse with the generated PDF
     */
    @PostMapping(path = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<ImageToPdfResponse> convertImagesToPdf(
        @RequestParam("images") List<MultipartFile> images,
        @RequestParam(value = "outputFileName", required = false) String outputFileName,
        @RequestParam(value = "pageSize", required = false) String pageSize
    );
} 