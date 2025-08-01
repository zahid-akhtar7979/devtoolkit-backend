package com.devtoolkit.tools.timestampconverter.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.timestampconverter.constants.TimestampConverterConstants;
import com.devtoolkit.tools.timestampconverter.dto.TimestampConverterResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Service implementation for Timestamp Converter operations
 */
@Service
public class TimestampConverterServiceImpl implements TimestampConverterService {
    
    @Override
    public TimestampConverterResponse convertTimestamp(String timestamp, String format) {
        if (timestamp == null || timestamp.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, TimestampConverterConstants.EMPTY_TIMESTAMP_MESSAGE);
        }
        
        try {
            long timestampLong = Long.parseLong(timestamp);
            Instant instant = Instant.ofEpochSecond(timestampLong);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            
            if (format == null || format.trim().isEmpty()) {
                format = TimestampConverterConstants.DEFAULT_DATE_FORMAT;
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String converted = dateTime.format(formatter);
            
            TimestampConverterResponse response = new TimestampConverterResponse();
            response.setOriginalTimestamp(timestamp);
            response.setConvertedDate(converted);
            response.setFormat(format);
            response.setTimestampValue(timestampLong);
            response.setSuccess(true);
            response.setMessage(TimestampConverterConstants.CONVERSION_SUCCESS_MESSAGE);
            
            return response;
        } catch (NumberFormatException e) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, e, TimestampConverterConstants.INVALID_TIMESTAMP_MESSAGE);
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, TimestampConverterConstants.CONVERSION_FAILED_MESSAGE);
        }
    }
} 