package com.devtoolkit.tools.diff;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.diff.api.IDiffResources;
import com.devtoolkit.tools.diff.dto.DiffRequest;
import com.devtoolkit.tools.diff.dto.DiffResponse;
import com.devtoolkit.tools.diff.service.DiffService;
import com.devtoolkit.tools.diff.validation.DiffRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiffController implements IDiffResources {
    
    @Autowired
    private DiffService diffService;
    
    @Autowired
    private DiffRequestValidator validator;
    
        @Override
    public ServiceResponse<DiffResponse> compareText(@RequestBody ServiceRequest<DiffRequest> request) {
        DiffRequest payload = request.getPayload();
        validator.validateRequest(payload);
        DiffResponse result = diffService.compareText(payload.getText1(), payload.getText2());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }

    @Override
    public ServiceResponse<DiffResponse> enhancedCompare(@RequestBody ServiceRequest<DiffRequest> request) {
        DiffRequest payload = request.getPayload();
        validator.validateRequest(payload);
        DiffResponse result = diffService.generateEnhancedDiff(payload);
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 