package com.devtoolkit.tools.hash;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.hash.api.IHashResources;
import com.devtoolkit.tools.hash.dto.HashRequest;
import com.devtoolkit.tools.hash.dto.HashResponse;
import com.devtoolkit.tools.hash.service.HashService;
import com.devtoolkit.tools.hash.validation.HashRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HashController implements IHashResources {
    
    @Autowired
    private HashService hashService;
    
    @Autowired
    private HashRequestValidator validator;
    
    @Override
    public ServiceResponse<HashResponse> generateHash(@RequestBody ServiceRequest<HashRequest> request) {
        HashRequest payload = request.getPayload();
        validator.validateRequestWithAlgorithm(payload);
        HashResponse result;
        
        if (payload.getAlgorithm() != null && !payload.getAlgorithm().trim().isEmpty()) {
            result = hashService.generateHash(payload.getText(), payload.getAlgorithm());
        } else {
            result = hashService.generateHash(payload.getText());
        }
        
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 