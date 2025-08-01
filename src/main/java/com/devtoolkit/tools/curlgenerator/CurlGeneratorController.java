package com.devtoolkit.tools.curlgenerator;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.curlgenerator.api.ICurlGeneratorResources;
import com.devtoolkit.tools.curlgenerator.dto.CurlGeneratorRequest;
import com.devtoolkit.tools.curlgenerator.dto.CurlGeneratorResponse;
import com.devtoolkit.tools.curlgenerator.service.CurlGeneratorService;
import com.devtoolkit.tools.curlgenerator.validation.CurlGeneratorRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for cURL Generator operations
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CurlGeneratorController implements ICurlGeneratorResources {
    
    @Autowired
    private CurlGeneratorService curlGeneratorService;
    
    @Autowired
    private CurlGeneratorRequestValidator validator;
    
    @Override
    public ServiceResponse<CurlGeneratorResponse> generateCurl(@RequestBody ServiceRequest<CurlGeneratorRequest> request) {
        CurlGeneratorRequest payload = request.getPayload();
        validator.validateRequest(payload);
        
        CurlGeneratorResponse result = curlGeneratorService.generateCurl(
            payload.getUrl(), payload.getMethod(), payload.getHeaders(), payload.getBody());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 