package com.devtoolkit.tools.cron.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.cron.dto.CronRequest;
import com.devtoolkit.tools.cron.dto.CronResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cron")
public interface ICronResources {

    @PostMapping(path = "/evaluate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<CronResponse> evaluateCron(@RequestBody ServiceRequest<CronRequest> request);
} 