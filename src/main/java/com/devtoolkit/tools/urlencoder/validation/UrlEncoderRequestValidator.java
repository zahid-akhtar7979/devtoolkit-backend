package com.devtoolkit.tools.urlencoder.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.urlencoder.constants.UrlEncoderConstants;
import com.devtoolkit.tools.urlencoder.dto.UrlEncoderRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for URLEncoder requests
 */
@Component
public class UrlEncoderRequestValidator {
    
    /**
     * Validates URLEncoder request
     * 
     * @param request the URLEncoder request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(UrlEncoderRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getText())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Text cannot be null or empty");
        }
        
        if (!StringUtils.hasText(request.getOperation())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Operation cannot be null or empty");
        }
        
        String operation = request.getOperation().toLowerCase();
        if (!operation.equals(UrlEncoderConstants.ENCODE_OPERATION) && 
            !operation.equals(UrlEncoderConstants.DECODE_OPERATION)) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Invalid operation. Must be 'encode' or 'decode'");
        }
    }
} 