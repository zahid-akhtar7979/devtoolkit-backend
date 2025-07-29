package com.devtoolkit.tools.curlgenerator.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.curlgenerator.dto.CurlGeneratorRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for cURL Generator requests
 */
@Component
public class CurlGeneratorRequestValidator {
    
    /**
     * Validates cURL Generator request
     * 
     * @param request the cURL Generator request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(CurlGeneratorRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getUrl())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "URL cannot be null or empty");
        }
    }
} 