package com.devtoolkit.tools.sqlformatter.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.sqlformatter.dto.SqlFormatterRequest;
import com.devtoolkit.tools.sqlformatter.dto.SqlFormatterResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for SQL Formatter operations
 */
@RequestMapping("/api/sqlformatter")
public interface ISqlFormatterResources {
    
    /**
     * Format SQL
     * 
     * @param request the SQL Formatter request
     * @return ServiceResponse with the formatted SQL
     */
    @PostMapping(path = "/format", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<SqlFormatterResponse> formatSql(@RequestBody ServiceRequest<SqlFormatterRequest> request);
} 