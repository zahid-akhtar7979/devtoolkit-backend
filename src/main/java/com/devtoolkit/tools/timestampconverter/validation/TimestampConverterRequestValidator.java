package com.devtoolkit.tools.timestampconverter.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.timestampconverter.dto.TimestampConverterRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Timestamp Converter requests
 */
@Component
public class TimestampConverterRequestValidator {
    
    /**
     * Validates Timestamp Converter request
     * 
     * @param request the Timestamp Converter request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(TimestampConverterRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getTimestamp())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Timestamp cannot be null or empty");
        }
    }
} 