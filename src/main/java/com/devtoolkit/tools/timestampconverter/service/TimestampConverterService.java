package com.devtoolkit.tools.timestampconverter.service;

import com.devtoolkit.tools.timestampconverter.dto.TimestampConverterResponse;

/**
 * Service interface for Timestamp Converter operations
 */
public interface TimestampConverterService {
    
    /**
     * Convert timestamp to readable date
     * 
     * @param timestamp the Unix timestamp
     * @param format the output date format (optional)
     * @return TimestampConverterResponse with the converted date
     */
    TimestampConverterResponse convertTimestamp(String timestamp, String format);
} 