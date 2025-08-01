package com.devtoolkit.tools.jwt.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.jwt.dto.JwtRequest;
import com.devtoolkit.tools.jwt.dto.JwtResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/jwt")
public interface IJwtResources {

    @PostMapping(path = "/decode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<JwtResponse> decodeToken(@RequestBody ServiceRequest<JwtRequest> request);

    @PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<Boolean> verifyToken(@RequestBody ServiceRequest<JwtRequest> request);
} 