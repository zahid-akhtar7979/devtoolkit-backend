package com.devtoolkit.tools.hash.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.hash.dto.HashRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Hash requests
 */
@Component
public class HashRequestValidator {
    
    /**
     * Validates Hash request
     * 
     * @param request the Hash request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(HashRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.HASH_INVALID_ALGORITHM, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getText())) {
            throw new DevToolkitException(ErrorCode.HASH_INVALID_ALGORITHM, "Text cannot be null or empty");
        }
    }
    
    /**
     * Validates Hash request with algorithm
     * 
     * @param request the Hash request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequestWithAlgorithm(HashRequest request) {
        validateRequest(request);
        
        if (StringUtils.hasText(request.getAlgorithm())) {
            // Additional algorithm validation can be added here
        }
    }
} 