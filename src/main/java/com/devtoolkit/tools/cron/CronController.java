package com.devtoolkit.tools.cron;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.cron.api.ICronResources;
import com.devtoolkit.tools.cron.dto.CronRequest;
import com.devtoolkit.tools.cron.dto.CronResponse;
import com.devtoolkit.tools.cron.service.CronService;
import com.devtoolkit.tools.cron.validation.CronRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CronController implements ICronResources {
    
    @Autowired
    private CronService cronService;
    
    @Autowired
    private CronRequestValidator validator;
    
    @Override
    public ServiceResponse<CronResponse> evaluateCron(@RequestBody ServiceRequest<CronRequest> request) {
        CronRequest payload = request.getPayload();
        validator.validateRequest(payload);
        CronResponse result = cronService.evaluateCron(payload.getCronExpression(), 5);
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 