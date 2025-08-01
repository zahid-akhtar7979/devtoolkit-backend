package com.devtoolkit.tools.jasypt.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.jasypt.dto.JasyptRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Jasypt requests
 */
@Component
public class JasyptRequestValidator {
    
    /**
     * Validates Jasypt request
     * 
     * @param request the Jasypt request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(JasyptRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.JASYPT_INVALID_KEY, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getText())) {
            throw new DevToolkitException(ErrorCode.JASYPT_INVALID_KEY, "Text cannot be null or empty");
        }
        
        if (!StringUtils.hasText(request.getPassword())) {
            throw new DevToolkitException(ErrorCode.JASYPT_INVALID_KEY, "Password cannot be null or empty");
        }
    }
} 