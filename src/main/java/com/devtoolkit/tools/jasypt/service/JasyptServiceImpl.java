package com.devtoolkit.tools.jasypt.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.jasypt.constants.JasyptConstants;
import com.devtoolkit.tools.jasypt.dto.JasyptResponse;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.stereotype.Service;

@Service
public class JasyptServiceImpl implements JasyptService {
    
    @Override
    public JasyptResponse encrypt(String text, String password, String algorithm) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.JASYPT_ENCRYPTION_ERROR, JasyptConstants.EMPTY_TEXT_MESSAGE);
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.JASYPT_INVALID_KEY, JasyptConstants.EMPTY_PASSWORD_MESSAGE);
        }
        
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(password);
            config.setAlgorithm(algorithm != null ? algorithm : JasyptConstants.DEFAULT_ALGORITHM);
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setProviderName(JasyptConstants.PROVIDER_NAME);
            config.setSaltGeneratorClassName(JasyptConstants.SALT_GENERATOR_CLASS);
            config.setStringOutputType(JasyptConstants.STRING_OUTPUT_TYPE);
            encryptor.setConfig(config);
            
            String encrypted = encryptor.encrypt(text);
            
            JasyptResponse response = new JasyptResponse();
            response.setOriginalText(text);
            response.setProcessedText(encrypted);
            response.setPassword(password);
            response.setAlgorithm(algorithm != null ? algorithm : JasyptConstants.DEFAULT_ALGORITHM);
            response.setOperation(JasyptConstants.ENCRYPT_OPERATION);
            response.setSuccess(true);
            response.setMessage(JasyptConstants.ENCRYPT_SUCCESS_MESSAGE);
            
            return response;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.JASYPT_ENCRYPTION_ERROR, e, JasyptConstants.ENCRYPTION_FAILED_MESSAGE);
        }
    }
    
    @Override
    public JasyptResponse decrypt(String text, String password, String algorithm) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.JASYPT_DECRYPTION_ERROR, JasyptConstants.EMPTY_TEXT_MESSAGE);
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.JASYPT_INVALID_KEY, JasyptConstants.EMPTY_PASSWORD_MESSAGE);
        }
        
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(password);
            config.setAlgorithm(algorithm != null ? algorithm : JasyptConstants.DEFAULT_ALGORITHM);
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setProviderName(JasyptConstants.PROVIDER_NAME);
            config.setSaltGeneratorClassName(JasyptConstants.SALT_GENERATOR_CLASS);
            config.setStringOutputType(JasyptConstants.STRING_OUTPUT_TYPE);
            encryptor.setConfig(config);
            
            String decrypted = encryptor.decrypt(text);
            
            JasyptResponse response = new JasyptResponse();
            response.setOriginalText(text);
            response.setProcessedText(decrypted);
            response.setPassword(password);
            response.setAlgorithm(algorithm != null ? algorithm : JasyptConstants.DEFAULT_ALGORITHM);
            response.setOperation(JasyptConstants.DECRYPT_OPERATION);
            response.setSuccess(true);
            response.setMessage(JasyptConstants.DECRYPT_SUCCESS_MESSAGE);
            
            return response;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.JASYPT_DECRYPTION_ERROR, e, JasyptConstants.DECRYPTION_FAILED_MESSAGE);
        }
    }
} 