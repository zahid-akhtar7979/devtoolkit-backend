package com.devtoolkit.tools.sqlformatter.constants;

/**
 * Constants for SQL Formatter operations
 */
public final class SqlFormatterConstants {
    
    private SqlFormatterConstants() {
        // Private constructor to prevent instantiation
    }
    
    // SQL Dialects
    public static final String MYSQL_DIALECT = "mysql";
    public static final String POSTGRESQL_DIALECT = "postgresql";
    public static final String ORACLE_DIALECT = "oracle";
    public static final String SQLSERVER_DIALECT = "sqlserver";
    public static final String SQLITE_DIALECT = "sqlite";
    public static final String STANDARD_DIALECT = "standard";
    
    // Default values
    public static final String DEFAULT_DIALECT = "standard";
    
    // Messages
    public static final String FORMAT_SUCCESS_MESSAGE = "SQL formatted successfully";
    public static final String EMPTY_SQL_MESSAGE = "SQL cannot be null or empty";
    public static final String INVALID_DIALECT_MESSAGE = "Invalid SQL dialect";
    
    // Error messages
    public static final String FORMAT_FAILED_MESSAGE = "Failed to format SQL";
} 