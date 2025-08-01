package com.devtoolkit.tools.formatconverter.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.formatconverter.dto.FormatConverterRequest;
import com.devtoolkit.tools.formatconverter.dto.FormatConverterResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for Format Converter operations
 */
@RequestMapping("/api/formatconverter")
public interface IFormatConverterResources {
    
    /**
     * Convert text between different formats
     * 
     * @param request the Format Converter request
     * @return ServiceResponse with the converted text
     */
    @PostMapping(path = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<FormatConverterResponse> convertFormat(@RequestBody ServiceRequest<FormatConverterRequest> request);
} 