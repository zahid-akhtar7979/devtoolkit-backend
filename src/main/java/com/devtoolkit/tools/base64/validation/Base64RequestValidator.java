package com.devtoolkit.tools.base64.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.base64.dto.Base64Request;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Base64 requests
 */
@Component
public class Base64RequestValidator {
    
    /**
     * Validates Base64 encode/decode request
     * 
     * @param request the Base64 request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(Base64Request request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.BASE64_INVALID_INPUT, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getText())) {
            throw new DevToolkitException(ErrorCode.BASE64_INVALID_INPUT, "Text cannot be null or empty");
        }
    }
} 