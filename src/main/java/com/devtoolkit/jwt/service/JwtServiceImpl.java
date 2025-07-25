package com.devtoolkit.jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public Map<String, Object> decodeToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token format");
            }
            
            Map<String, Object> result = new HashMap<>();
            
            // Decode header
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            Map<String, Object> header = objectMapper.readValue(headerJson, Map.class);
            result.put("header", header);
            
            // Decode payload
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            Map<String, Object> payload = objectMapper.readValue(payloadJson, Map.class);
            result.put("payload", payload);
            
            // Add signature (encoded)
            result.put("signature", parts[2]);
            
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decode JWT token: " + e.getMessage());
        }
    }
    
    @Override
    public boolean verifyToken(String token, String secret) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("Secret cannot be null or empty");
        }
        
        try {
            // This is a simplified verification
            // In a real application, you would use a proper JWT library
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }
            
            // For demonstration purposes, we'll just check if the token has the right format
            // In production, you should use a proper JWT library like jjwt
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 