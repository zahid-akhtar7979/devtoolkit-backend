package com.devtoolkit.tools.sqlformatter.validation;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.sqlformatter.dto.SqlFormatterRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for SQL Formatter requests
 */
@Component
public class SqlFormatterRequestValidator {
    
    /**
     * Validates SQL Formatter request
     * 
     * @param request the SQL Formatter request
     * @throws DevToolkitException if validation fails
     */
    public void validateRequest(SqlFormatterRequest request) {
        if (request == null) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "Request cannot be null");
        }
        
        if (!StringUtils.hasText(request.getSql())) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, "SQL cannot be null or empty");
        }
    }
} 