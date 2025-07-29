package com.devtoolkit.tools.jwt.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.jwt.dto.JwtRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for JWT requests
 */
@Component
public class JwtRequestValidator {
    
    /**
     * Validates JWT decode request
     * 
     * @param request the JWT request
     * @throws DevToolkitException if validation fails
     */
    public void validateDecodeRequest(JwtRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getToken())) {
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, "Token cannot be null or empty");
        }
    }
    
    /**
     * Validates JWT verify request
     * 
     * @param request the JWT request
     * @throws DevToolkitException if validation fails
     */
    public void validateVerifyRequest(JwtRequest request) {
        validateDecodeRequest(request);
        
        if (!StringUtils.hasText(request.getSecret())) {
            throw new DevToolkitException(ErrorCode.JWT_INVALID_SECRET, "Secret cannot be null or empty for verification");
        }
    }
} 