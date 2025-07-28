package com.devtoolkit.tools.jasypt.service;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.stereotype.Service;

@Service
public class JasyptServiceImpl implements JasyptService {
    
    @Override
    public String encrypt(String text, String password, String algorithm) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(password);
            config.setAlgorithm(algorithm != null ? algorithm : "PBEWithMD5AndDES");
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setProviderName("SunJCE");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setStringOutputType("base64");
            encryptor.setConfig(config);
            
            return encryptor.encrypt(text);
        } catch (Exception e) {
            throw new IllegalArgumentException("Encryption failed: " + e.getMessage());
        }
    }
    
    @Override
    public String decrypt(String text, String password, String algorithm) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(password);
            config.setAlgorithm(algorithm != null ? algorithm : "PBEWithMD5AndDES");
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setProviderName("SunJCE");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setStringOutputType("base64");
            encryptor.setConfig(config);
            
            return encryptor.decrypt(text);
        } catch (Exception e) {
            throw new IllegalArgumentException("Decryption failed: " + e.getMessage());
        }
    }
} 