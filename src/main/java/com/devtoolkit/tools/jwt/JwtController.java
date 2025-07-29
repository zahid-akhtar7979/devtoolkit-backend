package com.devtoolkit.tools.jwt;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.jwt.api.IJwtResources;
import com.devtoolkit.tools.jwt.dto.JwtRequest;
import com.devtoolkit.tools.jwt.dto.JwtResponse;
import com.devtoolkit.tools.jwt.service.JwtService;
import com.devtoolkit.tools.jwt.validation.JwtRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class JwtController implements IJwtResources {
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private JwtRequestValidator validator;
    
    @Override
    public ServiceResponse<JwtResponse> decodeToken(@RequestBody ServiceRequest<JwtRequest> request) {
        JwtRequest payload = request.getPayload();
        validator.validateDecodeRequest(payload);
        JwtResponse decoded = jwtService.decodeToken(payload.getToken());
        return new ServiceResponse<>(Status.SUCCESS, decoded);
    }
    
    @Override
    public ServiceResponse<Boolean> verifyToken(@RequestBody ServiceRequest<JwtRequest> request) {
        JwtRequest payload = request.getPayload();
        validator.validateVerifyRequest(payload);
        boolean isValid = jwtService.verifyToken(payload.getToken(), payload.getSecret());
        return new ServiceResponse<>(Status.SUCCESS, isValid);
    }
} 