package com.devtoolkit.tools.formatconverter;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.formatconverter.api.IFormatConverterResources;
import com.devtoolkit.tools.formatconverter.dto.FormatConverterRequest;
import com.devtoolkit.tools.formatconverter.dto.FormatConverterResponse;
import com.devtoolkit.tools.formatconverter.service.FormatConverterService;
import com.devtoolkit.tools.formatconverter.validation.FormatConverterRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Format Converter operations
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FormatConverterController implements IFormatConverterResources {
    
    @Autowired
    private FormatConverterService formatConverterService;
    
    @Autowired
    private FormatConverterRequestValidator validator;
    
    @Override
    public ServiceResponse<FormatConverterResponse> convertFormat(@RequestBody ServiceRequest<FormatConverterRequest> request) {
        FormatConverterRequest payload = request.getPayload();
        validator.validateRequest(payload);
        
        FormatConverterResponse result = formatConverterService.convertFormat(
            payload.getText(), payload.getSourceFormat(), payload.getTargetFormat());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 