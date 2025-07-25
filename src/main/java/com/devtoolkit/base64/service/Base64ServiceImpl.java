package com.devtoolkit.base64.service;

import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public class Base64ServiceImpl implements Base64Service {
    
    @Override
    public String encode(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
    
    @Override
    public String decode(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(text);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 string");
        }
    }
} 