package com.devtoolkit.tools.cron.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.cron.dto.CronRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for Cron requests
 */
@Component
public class CronRequestValidator {
    
    /**
     * Validates Cron request
     * 
     * @param request the Cron request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(CronRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.CRON_INVALID_EXPRESSION, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getCronExpression())) {
            throw new DevToolkitException(ErrorCode.CRON_INVALID_EXPRESSION, "Cron expression cannot be null or empty");
        }
    }
} 