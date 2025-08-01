package com.devtoolkit.tools.curlgenerator.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.curlgenerator.dto.CurlGeneratorRequest;
import com.devtoolkit.tools.curlgenerator.dto.CurlGeneratorResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for cURL Generator operations
 */
@RequestMapping("/api/curlgenerator")
public interface ICurlGeneratorResources {
    
    /**
     * Generate cURL command
     * 
     * @param request the cURL Generator request
     * @return ServiceResponse with the generated cURL command
     */
    @PostMapping(path = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<CurlGeneratorResponse> generateCurl(@RequestBody ServiceRequest<CurlGeneratorRequest> request);
} 