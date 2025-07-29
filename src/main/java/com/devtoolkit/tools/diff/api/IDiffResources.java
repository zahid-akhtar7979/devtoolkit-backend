package com.devtoolkit.tools.diff.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.diff.dto.DiffRequest;
import com.devtoolkit.tools.diff.dto.DiffResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/diff")
public interface IDiffResources {

    @PostMapping(path = "/compare", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<DiffResponse> compareText(@RequestBody ServiceRequest<DiffRequest> request);

    @PostMapping(path = "/enhanced", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<DiffResponse> enhancedCompare(@RequestBody ServiceRequest<DiffRequest> request);
} 