package com.devtoolkit.tools.regextester.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.regextester.dto.RegexTesterRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Regex Tester requests
 */
@Component
public class RegexTesterRequestValidator {
    
    /**
     * Validates Regex Tester request
     * 
     * @param request the Regex Tester request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(RegexTesterRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getPattern())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Pattern cannot be null or empty");
        }
    }
} 