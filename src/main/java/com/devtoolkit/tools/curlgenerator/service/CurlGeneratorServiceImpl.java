package com.devtoolkit.tools.curlgenerator.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.curlgenerator.constants.CurlGeneratorConstants;
import com.devtoolkit.tools.curlgenerator.dto.CurlGeneratorResponse;
import org.springframework.stereotype.Service;

/**
 * Service implementation for cURL Generator operations
 */
@Service
public class CurlGeneratorServiceImpl implements CurlGeneratorService {
    
    @Override
    public CurlGeneratorResponse generateCurl(String url, String method, String headers, String body) {
        if (url == null || url.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, CurlGeneratorConstants.EMPTY_URL_MESSAGE);
        }
        
        try {
            StringBuilder curl = new StringBuilder(CurlGeneratorConstants.CURL_COMMAND);
            
            if (method != null && !method.trim().isEmpty()) {
                curl.append(" -X ").append(method.toUpperCase());
            }
            
            if (headers != null && !headers.trim().isEmpty()) {
                curl.append(" -H \"").append(headers).append("\"");
            }
            
            if (body != null && !body.trim().isEmpty()) {
                curl.append(" -d '").append(body).append("'");
            }
            
            curl.append(" \"").append(url).append("\"");
            
            String curlCommand = curl.toString();
            
            CurlGeneratorResponse response = new CurlGeneratorResponse();
            response.setUrl(url);
            response.setMethod(method);
            response.setHeaders(headers);
            response.setBody(body);
            response.setCurlCommand(curlCommand);
            response.setSuccess(true);
            response.setMessage(CurlGeneratorConstants.GENERATION_SUCCESS_MESSAGE);
            
            return response;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, CurlGeneratorConstants.GENERATION_FAILED_MESSAGE);
        }
    }
} 