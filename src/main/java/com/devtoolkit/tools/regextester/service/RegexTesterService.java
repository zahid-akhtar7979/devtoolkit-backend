package com.devtoolkit.tools.regextester.service;

import com.devtoolkit.tools.regextester.dto.RegexTesterResponse;

/**
 * Service interface for Regex Tester operations
 */
public interface RegexTesterService {
    
    /**
     * Test regex pattern against text
     * 
     * @param pattern the regex pattern
     * @param text the text to test
     * @return RegexTesterResponse with the test result
     */
    RegexTesterResponse testRegex(String pattern, String text);
} 