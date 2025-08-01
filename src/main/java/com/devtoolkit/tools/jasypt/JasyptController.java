package com.devtoolkit.tools.jasypt;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.jasypt.api.IJasyptResources;
import com.devtoolkit.tools.jasypt.dto.JasyptRequest;
import com.devtoolkit.tools.jasypt.dto.JasyptResponse;
import com.devtoolkit.tools.jasypt.service.JasyptService;
import com.devtoolkit.tools.jasypt.validation.JasyptRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class JasyptController implements IJasyptResources {
    
    @Autowired
    private JasyptService jasyptService;
    
    @Autowired
    private JasyptRequestValidator validator;
    
    @Override
    public ServiceResponse<JasyptResponse> encrypt(@RequestBody ServiceRequest<JasyptRequest> request) {
        JasyptRequest payload = request.getPayload();
        validator.validateRequest(payload);
        JasyptResponse result = jasyptService.encrypt(payload.getText(), payload.getPassword(), payload.getAlgorithm());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
    
    @Override
    public ServiceResponse<JasyptResponse> decrypt(@RequestBody ServiceRequest<JasyptRequest> request) {
        JasyptRequest payload = request.getPayload();
        validator.validateRequest(payload);
        JasyptResponse result = jasyptService.decrypt(payload.getText(), payload.getPassword(), payload.getAlgorithm());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 