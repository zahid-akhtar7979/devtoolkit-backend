package com.devtoolkit.tools.sqlformatter.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.sqlformatter.constants.SqlFormatterConstants;
import com.devtoolkit.tools.sqlformatter.dto.SqlFormatterResponse;
import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.languages.Dialect;
import org.springframework.stereotype.Service;

/**
 * Service implementation for SQL Formatter operations
 */
@Service
public class SqlFormatterServiceImpl implements SqlFormatterService {
    
    @Override
    public SqlFormatterResponse formatSql(String sql, String dialect) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, SqlFormatterConstants.EMPTY_SQL_MESSAGE);
        }

        try {
            // Use sql-formatter library for proper formatting
            Dialect sqlDialect = getDialect(dialect);
            String formatted = SqlFormatter.of(sqlDialect).format(sql);
            
            SqlFormatterResponse response = new SqlFormatterResponse();
            response.setOriginalSql(sql);
            response.setFormattedSql(formatted);
            response.setDialect(dialect != null ? dialect : SqlFormatterConstants.DEFAULT_DIALECT);
            response.setSuccess(true);
            response.setMessage(SqlFormatterConstants.FORMAT_SUCCESS_MESSAGE);
            
            return response;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, SqlFormatterConstants.FORMAT_FAILED_MESSAGE);
        }
    }

    private Dialect getDialect(String dialect) {
        if (dialect == null) return Dialect.StandardSql;
        switch (dialect.toLowerCase()) {
            case SqlFormatterConstants.MYSQL_DIALECT: return Dialect.MySql;
            case SqlFormatterConstants.POSTGRESQL_DIALECT: return Dialect.PostgreSql;
            case SqlFormatterConstants.ORACLE_DIALECT: return Dialect.PlSql;
            case SqlFormatterConstants.SQLSERVER_DIALECT: return Dialect.TSql;
            case SqlFormatterConstants.SQLITE_DIALECT: return Dialect.StandardSql;
            default: return Dialect.StandardSql;
        }
    }
} 