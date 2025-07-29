package com.devtoolkit.tools.urlencoder.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.urlencoder.constants.UrlEncoderConstants;
import com.devtoolkit.tools.urlencoder.dto.UrlEncoderResponse;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Service implementation for URLEncoder operations
 */
@Service
public class UrlEncoderServiceImpl implements UrlEncoderService {
    
    @Override
    public UrlEncoderResponse processUrl(String text, String operation, String encoding) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, UrlEncoderConstants.EMPTY_TEXT_MESSAGE);
        }
        
        if (operation == null || operation.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, UrlEncoderConstants.INVALID_OPERATION_MESSAGE);
        }
        
        // Use default encoding if not provided
        String charsetName = encoding != null && !encoding.trim().isEmpty() 
            ? encoding.trim() 
            : UrlEncoderConstants.DEFAULT_ENCODING;
        
        try {
            Charset charset = Charset.forName(charsetName);
            String processedText;
            String operationType = operation.toLowerCase();
            
            UrlEncoderResponse response = new UrlEncoderResponse();
            response.setOriginalText(text);
            response.setEncoding(charsetName);
            
            if (UrlEncoderConstants.ENCODE_OPERATION.equals(operationType)) {
                processedText = URLEncoder.encode(text, charset);
                response.setOperation(UrlEncoderConstants.ENCODE_OPERATION);
                response.setMessage(UrlEncoderConstants.ENCODE_SUCCESS_MESSAGE);
            } else if (UrlEncoderConstants.DECODE_OPERATION.equals(operationType)) {
                processedText = URLDecoder.decode(text, charset);
                response.setOperation(UrlEncoderConstants.DECODE_OPERATION);
                response.setMessage(UrlEncoderConstants.DECODE_SUCCESS_MESSAGE);
            } else {
                throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                    UrlEncoderConstants.INVALID_OPERATION_MESSAGE);
            }
            
            response.setProcessedText(processedText);
            response.setSuccess(true);
            
            return response;
            
        } catch (IllegalArgumentException e) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, e, 
                UrlEncoderConstants.INVALID_ENCODING_MESSAGE + charsetName);
        } catch (Exception e) {
            String errorMessage = UrlEncoderConstants.ENCODE_OPERATION.equals(operation.toLowerCase()) 
                ? UrlEncoderConstants.ENCODE_ERROR_MESSAGE 
                : UrlEncoderConstants.DECODE_ERROR_MESSAGE;
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, e, errorMessage);
        }
    }
} 