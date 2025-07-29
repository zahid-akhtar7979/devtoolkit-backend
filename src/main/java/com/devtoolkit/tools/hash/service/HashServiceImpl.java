package com.devtoolkit.tools.hash.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.hash.constants.HashConstants;
import com.devtoolkit.tools.hash.dto.HashResponse;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class HashServiceImpl implements HashService {
    
    @Override
    public HashResponse generateHash(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.HASH_INVALID_ALGORITHM, HashConstants.EMPTY_TEXT_MESSAGE);
        }
        
        Map<String, String> hashes = new HashMap<>();
        String[] algorithms = {HashConstants.MD5_ALGORITHM, HashConstants.SHA1_ALGORITHM, HashConstants.SHA256_ALGORITHM, HashConstants.SHA512_ALGORITHM};
        
        for (String algorithm : algorithms) {
            hashes.put(algorithm, generateHashInternal(text, algorithm));
        }
        
        HashResponse response = new HashResponse();
        response.setOriginalText(text);
        response.setHashes(hashes);
        response.setSuccess(true);
        response.setMessage(HashConstants.MULTI_HASH_SUCCESS_MESSAGE);
        
        return response;
    }
    
    @Override
    public HashResponse generateHash(String text, String algorithm) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.HASH_INVALID_ALGORITHM, HashConstants.EMPTY_TEXT_MESSAGE);
        }
        
        String hash = generateHashInternal(text, algorithm);
        
        HashResponse response = new HashResponse();
        response.setOriginalText(text);
        response.setSpecificHash(hash);
        response.setAlgorithm(algorithm);
        response.setSuccess(true);
        response.setMessage(HashConstants.HASH_SUCCESS_MESSAGE);
        
        return response;
    }
    
    private String generateHashInternal(String text, String algorithm) {
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
            throw new DevToolkitException(ErrorCode.HASH_INVALID_ALGORITHM, e, algorithm);
        }
    }
} 