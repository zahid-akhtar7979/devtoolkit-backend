package com.devtoolkit.tools.diff.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.diff.dto.DiffRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Diff requests
 */
@Component
public class DiffRequestValidator {
    
    /**
     * Validates Diff request
     * 
     * @param request the Diff request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(DiffRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.DIFF_INVALID_INPUT, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getText1())) {
            throw new DevToolkitException(ErrorCode.DIFF_INVALID_INPUT, "Text1 cannot be null or empty");
        }
        
        if (!StringUtils.hasText(request.getText2())) {
            throw new DevToolkitException(ErrorCode.DIFF_INVALID_INPUT, "Text2 cannot be null or empty");
        }
    }
} 