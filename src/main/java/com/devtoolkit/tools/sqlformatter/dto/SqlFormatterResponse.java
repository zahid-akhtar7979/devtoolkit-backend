package com.devtoolkit.tools.sqlformatter.dto;

import lombok.Data;

/**
 * Response DTO for SQL Formatter operations
 */
@Data
public class SqlFormatterResponse {
    private String originalSql;
    private String formattedSql;
    private String dialect;
    private boolean success;
    private String message;
} 