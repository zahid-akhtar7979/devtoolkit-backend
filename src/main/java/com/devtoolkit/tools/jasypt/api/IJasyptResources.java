package com.devtoolkit.tools.jasypt.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.jasypt.dto.JasyptRequest;
import com.devtoolkit.tools.jasypt.dto.JasyptResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/jasypt")
public interface IJasyptResources {

    @PostMapping(path = "/encrypt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<JasyptResponse> encrypt(@RequestBody ServiceRequest<JasyptRequest> request);

    @PostMapping(path = "/decrypt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<JasyptResponse> decrypt(@RequestBody ServiceRequest<JasyptRequest> request);
} 