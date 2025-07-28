package com.devtoolkit.tools.imagetopdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/image-to-pdf")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageToPdfController {

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> convertImagesToPdf(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam(value = "outputFileName", required = false, defaultValue = "images-to-pdf.pdf") String outputFileName,
            @RequestParam(value = "pageSize", required = false, defaultValue = "A4") String pageSize
    ) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (images == null || images.isEmpty()) {
                response.put("success", false);
                response.put("error", "No images provided");
                return ResponseEntity.ok(response);
            }

            PDDocument document = new PDDocument();
            int imagesProcessed = 0;

            for (MultipartFile imageFile : images) {
                if (imageFile.isEmpty()) continue;

                try {
                    // Read the image
                    BufferedImage image = ImageIO.read(imageFile.getInputStream());
                    if (image == null) continue;

                    // Create PDF image object
                    PDImageXObject pdImage = PDImageXObject.createFromByteArray(
                        document, 
                        imageFile.getBytes(), 
                        imageFile.getOriginalFilename()
                    );

                    // Determine page size
                    PDRectangle pageSizeRect = getPageSize(pageSize, image);
                    PDPage page = new PDPage(pageSizeRect);
                    document.addPage(page);

                    // Calculate image dimensions to fit the page
                    float pageWidth = pageSizeRect.getWidth();
                    float pageHeight = pageSizeRect.getHeight();
                    float imageWidth = pdImage.getWidth();
                    float imageHeight = pdImage.getHeight();

                    // Calculate scaling to fit image on page while maintaining aspect ratio
                    float scaleX = pageWidth / imageWidth;
                    float scaleY = pageHeight / imageHeight;
                    float scale = Math.min(scaleX, scaleY);

                    float scaledWidth = imageWidth * scale;
                    float scaledHeight = imageHeight * scale;

                    // Center the image on the page
                    float x = (pageWidth - scaledWidth) / 2;
                    float y = (pageHeight - scaledHeight) / 2;

                    // Add image to page
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.drawImage(pdImage, x, y, scaledWidth, scaledHeight);
                    contentStream.close();

                    imagesProcessed++;

                } catch (Exception e) {
                    System.err.println("Error processing image " + imageFile.getOriginalFilename() + ": " + e.getMessage());
                }
            }

            if (imagesProcessed == 0) {
                document.close();
                response.put("success", false);
                response.put("error", "No valid images could be processed");
                return ResponseEntity.ok(response);
            }

            // Convert PDF to base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();

            String pdfContent = Base64.getEncoder().encodeToString(baos.toByteArray());

            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            data.put("message", "Successfully converted " + imagesProcessed + " images to PDF");
            data.put("pdfContent", pdfContent);
            data.put("fileName", outputFileName);
            data.put("fileSize", (long) baos.size());
            data.put("totalPages", imagesProcessed);
            data.put("imagesProcessed", imagesProcessed);

            response.put("success", true);
            response.put("data", data);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error creating PDF: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    private PDRectangle getPageSize(String pageSize, BufferedImage image) {
        if ("Letter".equalsIgnoreCase(pageSize)) {
            return PDRectangle.LETTER;
        } else if ("A4".equalsIgnoreCase(pageSize)) {
            return PDRectangle.A4;
        } else if ("auto".equalsIgnoreCase(pageSize) || pageSize == null) {
            // Use image dimensions for auto sizing
            float width = image.getWidth();
            float height = image.getHeight();
            return new PDRectangle(width, height);
        }
        return PDRectangle.A4; // Default fallback
    }
}
