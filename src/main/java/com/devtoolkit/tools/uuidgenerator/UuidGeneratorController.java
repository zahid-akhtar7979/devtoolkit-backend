package com.devtoolkit.tools.uuidgenerator;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.uuidgenerator.api.IUuidGeneratorResources;
import com.devtoolkit.tools.uuidgenerator.dto.UuidGeneratorRequest;
import com.devtoolkit.tools.uuidgenerator.dto.UuidGeneratorResponse;
import com.devtoolkit.tools.uuidgenerator.service.UuidGeneratorService;
import com.devtoolkit.tools.uuidgenerator.validation.UuidGeneratorRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for UUID Generator operations
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UuidGeneratorController implements IUuidGeneratorResources {
    
    @Autowired
    private UuidGeneratorService uuidGeneratorService;
    
    @Autowired
    private UuidGeneratorRequestValidator validator;
    
    @Override
    public ServiceResponse<UuidGeneratorResponse> generateUuid(@RequestBody ServiceRequest<UuidGeneratorRequest> request) {
        UuidGeneratorRequest payload = request.getPayload();
        validator.validateRequest(payload);
        
        String type = payload.getType() != null ? payload.getType() : "v4";
        Integer count = payload.getCount();
        
        UuidGeneratorResponse result;
        if (count != null && count > 1) {
            // Generate multiple UUIDs
            result = uuidGeneratorService.generateMultipleUuids(type, count);
        } else {
            // Generate single UUID
            result = uuidGeneratorService.generateUuid(type);
        }
        
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 