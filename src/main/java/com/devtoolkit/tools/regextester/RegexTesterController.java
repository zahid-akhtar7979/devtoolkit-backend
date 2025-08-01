package com.devtoolkit.tools.regextester;

import com.devtoolkit.common.dto.ServiceRequest;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.tools.regextester.api.IRegexTesterResources;
import com.devtoolkit.tools.regextester.dto.RegexTesterRequest;
import com.devtoolkit.tools.regextester.dto.RegexTesterResponse;
import com.devtoolkit.tools.regextester.service.RegexTesterService;
import com.devtoolkit.tools.regextester.validation.RegexTesterRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Regex Tester operations
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RegexTesterController implements IRegexTesterResources {
    
    @Autowired
    private RegexTesterService regexTesterService;
    
    @Autowired
    private RegexTesterRequestValidator validator;
    
    @Override
    public ServiceResponse<RegexTesterResponse> testRegex(@RequestBody ServiceRequest<RegexTesterRequest> request) {
        RegexTesterRequest payload = request.getPayload();
        validator.validateRequest(payload);
        
        RegexTesterResponse result = regexTesterService.testRegex(payload.getPattern(), payload.getText());
        return new ServiceResponse<>(Status.SUCCESS, result);
    }
} 