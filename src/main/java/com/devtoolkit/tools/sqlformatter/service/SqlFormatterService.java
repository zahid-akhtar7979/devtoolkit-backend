package com.devtoolkit.tools.sqlformatter.service;

import com.devtoolkit.tools.sqlformatter.dto.SqlFormatterResponse;

/**
 * Service interface for SQL Formatter operations
 */
public interface SqlFormatterService {
    
    /**
     * Format SQL with specified dialect
     * 
     * @param sql the SQL to format
     * @param dialect the SQL dialect
     * @return SqlFormatterResponse with the formatted SQL
     */
    SqlFormatterResponse formatSql(String sql, String dialect);
} 