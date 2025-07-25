package com.devtoolkit.hash.service;

import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HashServiceImpl implements HashService {
    
    @Override
    public Map<String, String> generateHash(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        Map<String, String> hashes = new HashMap<>();
        String[] algorithms = {"MD5", "SHA-1", "SHA-256", "SHA-512"};
        
        for (String algorithm : algorithms) {
            hashes.put(algorithm, generateHash(text, algorithm));
        }
        
        return hashes;
    }
    
    @Override
    public String generateHash(String text, String algorithm) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(text.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Unsupported hash algorithm: " + algorithm);
        }
    }
} 