package com.devtoolkit.tools.urlencoder;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.urlencoder.api.IUrlEncoderResources;
import com.devtoolkit.tools.urlencoder.dto.UrlEncoderRequest;
import com.devtoolkit.tools.urlencoder.dto.UrlEncoderResponse;
import com.devtoolkit.tools.urlencoder.service.UrlEncoderService;
import com.devtoolkit.tools.urlencoder.validation.UrlEncoderRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for URLEncoder operations
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UrlEncoderController implements IUrlEncoderResources {
    
    @Autowired
    private UrlEncoderService urlEncoderService;
    
    @Autowired
    private UrlEncoderRequestValidator validator;
    
    @Override
    public ServiceResponse<UrlEncoderResponse> processUrl(@RequestBody ServiceRequest<UrlEncoderRequest> request) {
        UrlEncoderRequest payload = request.getPayload();
        validator.validateRequest(payload);
        
        UrlEncoderResponse result = urlEncoderService.processUrl(
            payload.getText(), 
            payload.getOperation(), 
            payload.getEncoding()
        );
        
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 