package com.devtoolkit.tools.sqlformatter;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.sqlformatter.api.ISqlFormatterResources;
import com.devtoolkit.tools.sqlformatter.dto.SqlFormatterRequest;
import com.devtoolkit.tools.sqlformatter.dto.SqlFormatterResponse;
import com.devtoolkit.tools.sqlformatter.service.SqlFormatterService;
import com.devtoolkit.tools.sqlformatter.validation.SqlFormatterRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for SQL Formatter operations
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SqlFormatterController implements ISqlFormatterResources {
    
    @Autowired
    private SqlFormatterService sqlFormatterService;
    
    @Autowired
    private SqlFormatterRequestValidator validator;
    
    @Override
    public ServiceResponse<SqlFormatterResponse> formatSql(@RequestBody ServiceRequest<SqlFormatterRequest> request) {
        SqlFormatterRequest payload = request.getPayload();
        validator.validateRequest(payload);
        
        SqlFormatterResponse result = sqlFormatterService.formatSql(payload.getSql(), payload.getDialect());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 