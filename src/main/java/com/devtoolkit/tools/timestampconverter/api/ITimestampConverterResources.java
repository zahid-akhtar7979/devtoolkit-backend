package com.devtoolkit.tools.timestampconverter.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.timestampconverter.dto.TimestampConverterRequest;
import com.devtoolkit.tools.timestampconverter.dto.TimestampConverterResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for Timestamp Converter operations
 */
@RequestMapping("/api/timestampconverter")
public interface ITimestampConverterResources {
    
    /**
     * Convert timestamp to readable date
     * 
     * @param request the Timestamp Converter request
     * @return ServiceResponse with the converted date
     */
    @PostMapping(path = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<TimestampConverterResponse> convertTimestamp(@RequestBody ServiceRequest<TimestampConverterRequest> request);
} 