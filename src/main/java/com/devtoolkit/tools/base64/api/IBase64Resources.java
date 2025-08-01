package com.devtoolkit.tools.base64.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.base64.dto.Base64Request;
import com.devtoolkit.tools.base64.dto.Base64Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/base64")
public interface IBase64Resources {

    @PostMapping(path = "/encode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<Base64Response> encode(@RequestBody ServiceRequest<Base64Request> request);

    @PostMapping(path = "/decode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<Base64Response> decode(@RequestBody ServiceRequest<Base64Request> request);
} 