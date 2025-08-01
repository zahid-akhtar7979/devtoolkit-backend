package com.devtoolkit.tools.base64.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.base64.constants.Base64Constants;
import com.devtoolkit.tools.base64.dto.Base64Response;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public class Base64ServiceImpl implements Base64Service {
    
        @Override
    public Base64Response encode(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.BASE64_INVALID_INPUT, Base64Constants.EMPTY_TEXT_MESSAGE);
        }
        try {
            String encoded = Base64.getEncoder().encodeToString(text.getBytes());
            Base64Response response = new Base64Response();
            response.setOriginalText(text);
            response.setProcessedText(encoded);
            response.setOperation(Base64Constants.ENCODE_OPERATION);
            response.setSuccess(true);
            response.setMessage(Base64Constants.ENCODE_SUCCESS_MESSAGE);
            return response;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.BASE64_ENCODE_ERROR, e, text);
        }
    }

    @Override
    public Base64Response decode(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.BASE64_INVALID_INPUT, Base64Constants.EMPTY_TEXT_MESSAGE);
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(text);
            String decoded = new String(decodedBytes);
            Base64Response response = new Base64Response();
            response.setOriginalText(text);
            response.setProcessedText(decoded);
            response.setOperation(Base64Constants.DECODE_OPERATION);
            response.setSuccess(true);
            response.setMessage(Base64Constants.DECODE_SUCCESS_MESSAGE);
            return response;
        } catch (IllegalArgumentException e) {
            throw new DevToolkitException(ErrorCode.BASE64_DECODE_ERROR, e, text);
        }
    }
} 