package com.devtoolkit.tools.curlgenerator.service;

import com.devtoolkit.tools.curlgenerator.dto.CurlGeneratorResponse;

/**
 * Service interface for cURL Generator operations
 */
public interface CurlGeneratorService {
    
    /**
     * Generate cURL command from parameters
     * 
     * @param url the URL
     * @param method the HTTP method
     * @param headers the headers (optional)
     * @param body the request body (optional)
     * @return CurlGeneratorResponse with the generated cURL command
     */
    CurlGeneratorResponse generateCurl(String url, String method, String headers, String body);
} 