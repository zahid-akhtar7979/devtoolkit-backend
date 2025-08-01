package com.devtoolkit.tools.urlencoder.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.urlencoder.dto.UrlEncoderRequest;
import com.devtoolkit.tools.urlencoder.dto.UrlEncoderResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for URLEncoder operations
 */
@RequestMapping("/api/urlencoder")
public interface IUrlEncoderResources {
    
    /**
     * Process URL encoding/decoding
     * 
     * @param request the URLEncoder request
     * @return ServiceResponse with the processed result
     */
    @PostMapping(path = "/process", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<UrlEncoderResponse> processUrl(@RequestBody ServiceRequest<UrlEncoderRequest> request);
} 