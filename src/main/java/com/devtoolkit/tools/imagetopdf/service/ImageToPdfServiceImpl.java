package com.devtoolkit.tools.imagetopdf.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.imagetopdf.constants.ImageToPdfConstants;
import com.devtoolkit.tools.imagetopdf.dto.ImageToPdfResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * Service implementation for ImageToPdf operations
 * 
 * <p>This service handles the conversion of multiple image files to a single PDF document.
 * It supports various page sizes and maintains image aspect ratios while centering images on pages.</p>
 * 
 * <p><strong>Note:</strong> This service assumes that all input validation has been performed
 * at the controller level. The service focuses purely on business logic.</p>
 * 
 * @author DevToolkit
 * @version 1.0
 */
@Slf4j
@Service
public class ImageToPdfServiceImpl implements ImageToPdfService {
    
    /**
     * Converts a list of image files to a single PDF document
     * 
     * <p><strong>Precondition:</strong> All input validation should be performed at the controller level.
     * This method assumes that the input parameters are valid.</p>
     * 
     * @param images the list of image files to convert (assumed to be validated)
     * @param outputFileName the desired output filename for the PDF
     * @param pageSize the page size for the PDF (A4, Letter, auto)
     * @return ImageToPdfResponse containing the generated PDF and metadata
     * @throws DevToolkitException if conversion fails or no valid images are provided
     */
    @Override
    public ImageToPdfResponse convertImagesToPdf(List<MultipartFile> images, String outputFileName, String pageSize) {
        // Normalize parameters (no validation - assumed to be done at controller level)
        String normalizedFileName = normalizeFileName(outputFileName);
        String normalizedPageSize = normalizePageSize(pageSize);
        
        log.info("Starting PDF conversion for {} images with page size: {}", images.size(), normalizedPageSize);
        
        try (PDDocument document = new PDDocument()) {
            // Process images and build PDF
            int processedCount = processImagesToPdf(document, images, normalizedPageSize);
            
            if (processedCount == 0) {
                throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, 
                    ImageToPdfConstants.NO_VALID_IMAGES_MESSAGE);
            }
            
            // Generate PDF content
            byte[] pdfBytes = generatePdfBytes(document);
            String pdfContent = Base64.getEncoder().encodeToString(pdfBytes);
            
            log.info("Successfully converted {} images to PDF", processedCount);
            
            // Build and return response
            return buildSuccessResponse(pdfContent, normalizedFileName, pdfBytes.length, 
                processedCount, normalizedPageSize);
            
        } catch (DevToolkitException e) {
            log.error("PDF conversion failed with DevToolkitException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during PDF conversion: {}", e.getMessage(), e);
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, "Error creating PDF");
        }
    }
    
    /**
     * Normalizes the output filename, providing a default if null or empty
     * 
     * @param outputFileName the original filename
     * @return the normalized filename
     */
    private String normalizeFileName(String outputFileName) {
        return StringUtils.hasText(outputFileName) 
            ? outputFileName.trim() 
            : ImageToPdfConstants.DEFAULT_OUTPUT_FILENAME;
    }
    
    /**
     * Normalizes the page size, providing a default if null or empty
     * 
     * @param pageSize the original page size
     * @return the normalized page size
     */
    private String normalizePageSize(String pageSize) {
        return StringUtils.hasText(pageSize) 
            ? pageSize.trim() 
            : ImageToPdfConstants.DEFAULT_PAGE_SIZE;
    }
    
    /**
     * Processes all images and adds them to the PDF document
     * 
     * @param document the PDF document to add pages to
     * @param images the list of images to process (assumed to be validated)
     * @param pageSize the page size to use
     * @return the number of successfully processed images
     */
    private int processImagesToPdf(PDDocument document, List<MultipartFile> images, String pageSize) {
        int processedCount = 0;
        
        for (MultipartFile imageFile : images) {
            if (imageFile.isEmpty()) {
                log.debug("Skipping empty image file: {}", imageFile.getOriginalFilename());
                continue;
            }
            
            try {
                if (processSingleImage(document, imageFile, pageSize)) {
                    processedCount++;
                }
            } catch (Exception e) {
                log.warn("Failed to process image {}: {}", imageFile.getOriginalFilename(), e.getMessage());
                // Continue processing other images
            }
        }
        
        return processedCount;
    }
    
    /**
     * Processes a single image and adds it to the PDF document
     * 
     * @param document the PDF document
     * @param imageFile the image file to process (assumed to be validated)
     * @param pageSize the page size to use
     * @return true if the image was successfully processed, false otherwise
     * @throws IOException if there's an error reading the image
     */
    private boolean processSingleImage(PDDocument document, MultipartFile imageFile, String pageSize) 
            throws IOException {
        
        // Read and validate image
        BufferedImage image = readImage(imageFile);
        if (image == null) {
            log.warn("Could not read image: {}", imageFile.getOriginalFilename());
            return false;
        }
        
        // Create PDF image object
        PDImageXObject pdImage = createPdfImage(document, imageFile);
        
        // Create page and add image
        PDPage page = createPageWithImage(document, pdImage, image, pageSize);
        
        log.debug("Successfully processed image: {}", imageFile.getOriginalFilename());
        return true;
    }
    
    /**
     * Reads an image from a MultipartFile
     * 
     * @param imageFile the image file to read
     * @return the BufferedImage, or null if reading fails
     */
    private BufferedImage readImage(MultipartFile imageFile) {
        try {
            return ImageIO.read(imageFile.getInputStream());
        } catch (IOException e) {
            log.warn("Failed to read image {}: {}", imageFile.getOriginalFilename(), e.getMessage());
            return null;
        }
    }
    
    /**
     * Creates a PDF image object from a MultipartFile
     * 
     * @param document the PDF document
     * @param imageFile the image file
     * @return the PDImageXObject
     * @throws IOException if there's an error creating the image object
     */
    private PDImageXObject createPdfImage(PDDocument document, MultipartFile imageFile) throws IOException {
        return PDImageXObject.createFromByteArray(
            document, 
            imageFile.getBytes(), 
            imageFile.getOriginalFilename()
        );
    }
    
    /**
     * Creates a PDF page with an image centered and scaled appropriately
     * 
     * @param document the PDF document
     * @param pdImage the PDF image object
     * @param originalImage the original BufferedImage for size calculations
     * @param pageSize the page size to use
     * @return the created PDF page
     * @throws IOException if there's an error creating the page
     */
    private PDPage createPageWithImage(PDDocument document, PDImageXObject pdImage, 
                                     BufferedImage originalImage, String pageSize) throws IOException {
        
        // Determine page size
        PDRectangle pageSizeRect = getPageSize(pageSize, originalImage);
        PDPage page = new PDPage(pageSizeRect);
        document.addPage(page);
        
        // Calculate image positioning and scaling
        ImagePosition position = calculateImagePosition(pdImage, pageSizeRect);
        
        // Draw image on page
        drawImageOnPage(document, page, pdImage, position);
        
        return page;
    }
    
    /**
     * Calculates the optimal position and scaling for an image on a page
     * 
     * @param pdImage the PDF image object
     * @param pageSizeRect the page size rectangle
     * @return ImagePosition containing x, y, width, and height
     */
    private ImagePosition calculateImagePosition(PDImageXObject pdImage, PDRectangle pageSizeRect) {
        float pageWidth = pageSizeRect.getWidth();
        float pageHeight = pageSizeRect.getHeight();
        float imageWidth = pdImage.getWidth();
        float imageHeight = pdImage.getHeight();
        
        // Calculate scaling to fit image on page while maintaining aspect ratio
        float scaleX = pageWidth / imageWidth;
        float scaleY = pageHeight / imageHeight;
        float scale = Math.min(scaleX, scaleY);
        
        // Calculate scaled dimensions
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;
        
        // Center the image on the page
        float x = (pageWidth - scaledWidth) / 2;
        float y = (pageHeight - scaledHeight) / 2;
        
        return new ImagePosition(x, y, scaledWidth, scaledHeight);
    }
    
    /**
     * Draws an image on a PDF page
     * 
     * @param document the PDF document
     * @param page the PDF page
     * @param pdImage the PDF image object
     * @param position the calculated image position
     * @throws IOException if there's an error drawing the image
     */
    private void drawImageOnPage(PDDocument document, PDPage page, PDImageXObject pdImage, 
                               ImagePosition position) throws IOException {
        
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.drawImage(pdImage, position.x, position.y, position.width, position.height);
        }
    }
    
    /**
     * Generates PDF bytes from the document
     * 
     * @param document the PDF document
     * @return the PDF as byte array
     * @throws IOException if there's an error saving the document
     */
    private byte[] generatePdfBytes(PDDocument document) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            document.save(baos);
            return baos.toByteArray();
        }
    }
    
    /**
     * Builds a successful response with all the necessary information
     * 
     * @param pdfContent the Base64 encoded PDF content
     * @param fileName the output filename
     * @param fileSize the file size in bytes
     * @param imagesProcessed the number of images processed
     * @param pageSize the page size used
     * @return the ImageToPdfResponse
     */
    private ImageToPdfResponse buildSuccessResponse(String pdfContent, String fileName, 
                                                  int fileSize, int imagesProcessed, String pageSize) {
        
        ImageToPdfResponse response = new ImageToPdfResponse();
        response.setPdfContent(pdfContent);
        response.setFileName(fileName);
        response.setFileSize((long) fileSize);
        response.setTotalPages(imagesProcessed);
        response.setImagesProcessed(imagesProcessed);
        response.setPageSize(pageSize);
        response.setSuccess(true);
        response.setMessage(String.format("%s %d images", 
            ImageToPdfConstants.CONVERSION_SUCCESS_MESSAGE, imagesProcessed));
        
        return response;
    }
    
    /**
     * Determines the appropriate page size based on the specified page size and image dimensions
     * 
     * @param pageSize the requested page size
     * @param image the image to use for auto-sizing calculations
     * @return the PDRectangle representing the page size
     */
    private PDRectangle getPageSize(String pageSize, BufferedImage image) {
        Objects.requireNonNull(image, "Image cannot be null for page size calculation");
        
        if (ImageToPdfConstants.PAGE_SIZE_LETTER.equalsIgnoreCase(pageSize)) {
            return PDRectangle.LETTER;
        } else if (ImageToPdfConstants.PAGE_SIZE_A4.equalsIgnoreCase(pageSize)) {
            return PDRectangle.A4;
        } else if (ImageToPdfConstants.PAGE_SIZE_AUTO.equalsIgnoreCase(pageSize)) {
            // Use image dimensions for auto sizing
            return new PDRectangle(image.getWidth(), image.getHeight());
        }
        
        // Default fallback
        log.debug("Using default A4 page size for unrecognized page size: {}", pageSize);
        return PDRectangle.A4;
    }
    
    /**
     * Immutable data class to hold image position and dimensions
     */
    private static class ImagePosition {
        final float x;
        final float y;
        final float width;
        final float height;
        
        ImagePosition(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
} 