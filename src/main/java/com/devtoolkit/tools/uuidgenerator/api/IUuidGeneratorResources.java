package com.devtoolkit.tools.uuidgenerator.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.uuidgenerator.dto.UuidGeneratorRequest;
import com.devtoolkit.tools.uuidgenerator.dto.UuidGeneratorResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for UUID Generator operations
 */
@RequestMapping("/api/uuidgenerator")
public interface IUuidGeneratorResources {
    
    /**
     * Generate UUID(s)
     * 
     * @param request the UUID Generator request
     * @return ServiceResponse with the generated UUID(s)
     */
    @PostMapping(path = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<UuidGeneratorResponse> generateUuid(@RequestBody ServiceRequest<UuidGeneratorRequest> request);
} 