package com.devtoolkit.tools.regextester.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.regextester.constants.RegexTesterConstants;
import com.devtoolkit.tools.regextester.dto.RegexTesterResponse;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Service implementation for Regex Tester operations
 */
@Service
public class RegexTesterServiceImpl implements RegexTesterService {
    
    @Override
    public RegexTesterResponse testRegex(String pattern, String text) {
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, RegexTesterConstants.EMPTY_PATTERN_MESSAGE);
        }
        
        try {
            Pattern regex = Pattern.compile(pattern);
            boolean matches = regex.matcher(text != null ? text : "").matches();
            String result = matches ? RegexTesterConstants.PATTERN_MATCHES_MESSAGE : RegexTesterConstants.PATTERN_NOT_MATCHES_MESSAGE;
            
            RegexTesterResponse response = new RegexTesterResponse();
            response.setPattern(pattern);
            response.setText(text);
            response.setMatches(matches);
            response.setResult(result);
            response.setSuccess(true);
            response.setMessage(RegexTesterConstants.TEST_SUCCESS_MESSAGE);
            
            return response;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, e, RegexTesterConstants.INVALID_REGEX_PATTERN_MESSAGE);
        }
    }
} 