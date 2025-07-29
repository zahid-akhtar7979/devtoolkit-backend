package com.devtoolkit.tools.sqlformatter.dto;

import lombok.Data;

/**
 * Request DTO for SQL Formatter operations
 */
@Data
public class SqlFormatterRequest {
    private String sql;
    private String dialect; // mysql, postgresql, oracle, sqlserver, sqlite, standard
} 