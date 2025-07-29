package com.devtoolkit.tools.hash.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.hash.dto.HashRequest;
import com.devtoolkit.tools.hash.dto.HashResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/hash")
public interface IHashResources {

    @PostMapping(path = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<HashResponse> generateHash(@RequestBody ServiceRequest<HashRequest> request);
} 