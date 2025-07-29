package com.devtoolkit.tools.uuidgenerator.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.uuidgenerator.constants.UuidGeneratorConstants;
import com.devtoolkit.tools.uuidgenerator.dto.UuidGeneratorRequest;
import org.springframework.stereotype.Component;

/**
 * Validator for UUID Generator requests
 */
@Component
public class UuidGeneratorRequestValidator {
    
    /**
     * Validates UUID Generator request
     * 
     * @param request the UUID Generator request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(UuidGeneratorRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        // Validate count if provided
        if (request.getCount() != null) {
            if (request.getCount() <= 0) {
                throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Count must be greater than 0");
            }
            if (request.getCount() > UuidGeneratorConstants.MAX_COUNT) {
                throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                    "Count cannot exceed " + UuidGeneratorConstants.MAX_COUNT);
            }
        }
    }
} 