package com.devtoolkit.jwt.service;

import com.devtoolkit.jwt.exception.JwtException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public Map<String, Object> decodeToken(String token) {
        // Validate input
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException.EmptyToken();
        }

        // Remove common prefixes if present
        token = token.trim();
        if (token.toLowerCase().startsWith("bearer ")) {
            token = token.substring(7);
        }

        // Validate JWT format
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            if (parts.length < 3) {
                throw new JwtException.InvalidFormat("Token has only " + parts.length + " parts, expected 3 (header.payload.signature)");
            } else {
                throw new JwtException.InvalidFormat("Token has " + parts.length + " parts, expected 3 (header.payload.signature)");
            }
        }

        // Check for empty parts
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].trim().isEmpty()) {
                String partName = i == 0 ? "header" : (i == 1 ? "payload" : "signature");
                throw new JwtException.InvalidFormat("Empty " + partName + " part");
            }
        }

        Map<String, Object> result = new HashMap<>();

        try {
            // Decode and validate header
            try {
                String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
                Map<String, Object> header = objectMapper.readValue(headerJson, Map.class);
                result.put("header", header);
            } catch (IllegalArgumentException e) {
                throw new JwtException.InvalidEncoding("Invalid Base64 encoding in header: " + e.getMessage());
            } catch (Exception e) {
                throw new JwtException.InvalidJson("Invalid JSON in header: " + e.getMessage());
            }

            // Decode and validate payload
            try {
                String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
                Map<String, Object> payload = objectMapper.readValue(payloadJson, Map.class);
                result.put("payload", payload);
            } catch (IllegalArgumentException e) {
                throw new JwtException.InvalidEncoding("Invalid Base64 encoding in payload: " + e.getMessage());
            } catch (Exception e) {
                throw new JwtException.InvalidJson("Invalid JSON in payload: " + e.getMessage());
            }

            // Add signature (encoded) - validate it's proper Base64
            try {
                Base64.getUrlDecoder().decode(parts[2]);
                result.put("signature", parts[2]);
            } catch (IllegalArgumentException e) {
                // Still include the signature even if invalid for debugging
                result.put("signature", parts[2]);
                throw new JwtException.InvalidEncoding("Invalid Base64 encoding in signature: " + e.getMessage());
            }

            return result;
        } catch (JwtException e) {
            // Re-throw JWT exceptions as-is
            throw e;
        } catch (Exception e) {
            throw new JwtException.MalformedToken("Unexpected error during token decoding: " + e.getMessage());
        }
    }
    
    @Override
    public boolean verifyToken(String token, String secret) {
        // Validate inputs
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException.EmptyToken();
        }
        
        if (secret == null || secret.trim().isEmpty()) {
            throw new JwtException.EmptySecret();
        }

        try {
            // Remove common prefixes if present
            token = token.trim();
            if (token.toLowerCase().startsWith("bearer ")) {
                token = token.substring(7);
            }

            // First, try to decode the token to ensure it's valid
            decodeToken(token);

            // Use JJWT library to properly verify the token
            try {
                // Create key from secret
                byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
                
                // Parse and verify the token
                Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
                
                // If we reach here, the token is valid
                return true;
                
            } catch (ExpiredJwtException e) {
                throw new JwtException.VerificationFailed("JWT token has expired");
            } catch (UnsupportedJwtException e) {
                throw new JwtException.VerificationFailed("Unsupported JWT token format");
            } catch (MalformedJwtException e) {
                throw new JwtException.VerificationFailed("Malformed JWT token");
            } catch (SignatureException e) {
                throw new JwtException.VerificationFailed("Invalid JWT signature - the token may have been tampered with or signed with a different secret");
            } catch (IllegalArgumentException e) {
                throw new JwtException.VerificationFailed("Invalid JWT token: " + e.getMessage());
            }
            
        } catch (JwtException e) {
            // Re-throw JWT exceptions as-is
            throw e;
        } catch (Exception e) {
            throw new JwtException.VerificationFailed("Unexpected error during verification: " + e.getMessage());
        }
    }
} 