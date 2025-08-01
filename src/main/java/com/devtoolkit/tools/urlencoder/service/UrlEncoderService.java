package com.devtoolkit.tools.urlencoder.service;

import com.devtoolkit.tools.urlencoder.dto.UrlEncoderResponse;

/**
 * Service interface for URLEncoder operations
 */
public interface UrlEncoderService {
    
    /**
     * Process URL encoding/decoding based on the operation
     * 
     * @param text the text to process
     * @param operation the operation to perform ("encode" or "decode")
     * @param encoding the character encoding to use (optional)
     * @return UrlEncoderResponse with the processed result
     */
    UrlEncoderResponse processUrl(String text, String operation, String encoding);
} 