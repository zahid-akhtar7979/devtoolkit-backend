package com.devtoolkit.tools.regextester.api;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.tools.regextester.dto.RegexTesterRequest;
import com.devtoolkit.tools.regextester.dto.RegexTesterResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for Regex Tester operations
 */
@RequestMapping("/api/regextester")
public interface IRegexTesterResources {
    
    /**
     * Test regex pattern
     * 
     * @param request the Regex Tester request
     * @return ServiceResponse with the test result
     */
    @PostMapping(path = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ServiceResponse<RegexTesterResponse> testRegex(@RequestBody ServiceRequest<RegexTesterRequest> request);
} 