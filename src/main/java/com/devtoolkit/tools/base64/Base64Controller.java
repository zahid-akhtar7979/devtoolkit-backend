package com.devtoolkit.tools.base64;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.base64.api.IBase64Resources;
import com.devtoolkit.tools.base64.dto.Base64Request;
import com.devtoolkit.tools.base64.dto.Base64Response;
import com.devtoolkit.tools.base64.service.Base64Service;
import com.devtoolkit.tools.base64.validation.Base64RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Base64Controller implements IBase64Resources {
    
    @Autowired
    private Base64Service base64Service;
    
    @Autowired
    private Base64RequestValidator validator;
    
        @Override
    public ServiceResponse<Base64Response> encode(@RequestBody ServiceRequest<Base64Request> request) {
        Base64Request payload = request.getPayload();
        validator.validateRequest(payload);
        Base64Response encoded = base64Service.encode(payload.getText());
        return new ServiceResponse<>(Status.SUCCESS, encoded);
    }

    @Override
    public ServiceResponse<Base64Response> decode(@RequestBody ServiceRequest<Base64Request> request) {
        Base64Request payload = request.getPayload();
        validator.validateRequest(payload);
        Base64Response decoded = base64Service.decode(payload.getText());
        return new ServiceResponse<>(Status.SUCCESS, decoded);
    }
} 