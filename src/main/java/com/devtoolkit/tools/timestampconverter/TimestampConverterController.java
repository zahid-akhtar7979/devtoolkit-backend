package com.devtoolkit.tools.timestampconverter;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.timestampconverter.api.ITimestampConverterResources;
import com.devtoolkit.tools.timestampconverter.dto.TimestampConverterRequest;
import com.devtoolkit.tools.timestampconverter.dto.TimestampConverterResponse;
import com.devtoolkit.tools.timestampconverter.service.TimestampConverterService;
import com.devtoolkit.tools.timestampconverter.validation.TimestampConverterRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Timestamp Converter operations
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TimestampConverterController implements ITimestampConverterResources {
    
    @Autowired
    private TimestampConverterService timestampConverterService;
    
    @Autowired
    private TimestampConverterRequestValidator validator;
    
    @Override
    public ServiceResponse<TimestampConverterResponse> convertTimestamp(@RequestBody ServiceRequest<TimestampConverterRequest> request) {
        TimestampConverterRequest payload = request.getPayload();
        validator.validateRequest(payload);
        
        TimestampConverterResponse result = timestampConverterService.convertTimestamp(payload.getTimestamp(), payload.getFormat());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 