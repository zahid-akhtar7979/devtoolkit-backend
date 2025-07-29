package com.devtoolkit.tools.formatconverter.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.formatconverter.constants.FormatConverterConstants;
import com.devtoolkit.tools.formatconverter.dto.FormatConverterRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Format Converter requests
 */
@Component
public class FormatConverterRequestValidator {
    
    /**
     * Validates Format Converter request
     * 
     * @param request the Format Converter request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(FormatConverterRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getText())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Text cannot be null or empty");
        }
        
        if (!StringUtils.hasText(request.getSourceFormat())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Source format is required");
        }
        
        if (!StringUtils.hasText(request.getTargetFormat())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Target format is required");
        }
        
        // Validate formats
        String sourceFormat = request.getSourceFormat().toUpperCase();
        String targetFormat = request.getTargetFormat().toUpperCase();
        
        if (!isValidFormat(sourceFormat)) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                "Invalid source format. Supported formats: JSON, YAML, XML");
        }
        
        if (!isValidFormat(targetFormat)) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                "Invalid target format. Supported formats: JSON, YAML, XML");
        }
    }
    
    private boolean isValidFormat(String format) {
        return FormatConverterConstants.JSON_FORMAT.equals(format) ||
               FormatConverterConstants.YAML_FORMAT.equals(format) ||
               FormatConverterConstants.XML_FORMAT.equals(format);
    }
} 