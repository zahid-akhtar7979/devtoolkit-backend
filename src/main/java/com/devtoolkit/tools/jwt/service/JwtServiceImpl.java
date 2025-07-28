package com.devtoolkit.tools.jwt.service;

import com.devtoolkit.tools.jwt.exception.JwtException;
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
        token = normalizeToken(token);
        String[] parts = validateTokenFormat(token);
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("header", decodeBase64Json(parts[0], "header"));
            result.put("payload", decodeBase64Json(parts[1], "payload"));
            try {
                Base64.getUrlDecoder().decode(parts[2]);
            } catch (IllegalArgumentException e) {
                result.put("signature", parts[2]);
                throw new JwtException.InvalidEncoding("Invalid Base64 encoding in signature: " + e.getMessage());
            }
            result.put("signature", parts[2]);
            return result;

        } catch (JwtException e) {
            throw e;
        } catch (Exception e) {
            throw new JwtException.MalformedToken("Unexpected error during token decoding: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyToken(String token, String secret) {
        validateTokenAndSecret(token, secret);
        try {
            token = normalizeToken(token);
            decodeToken(token);
            Claims claims = parseJwtClaims(token, secret);
            return claims != null;
        } catch (JwtException e) {
            throw e;
        } catch (Exception e) {
            throw new JwtException.VerificationFailed("Unexpected error during verification: " + e.getMessage());
        }
    }

    private String normalizeToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException.EmptyToken();
        }
        token = token.trim();
        if (token.toLowerCase().startsWith("bearer ")) {
            token = token.substring(7);
        }
        return token;
    }
    private String[] validateTokenFormat(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new JwtException.InvalidFormat("Token has " + parts.length + " parts, expected 3 (header.payload.signature)");
        }
        String[] partNames = {"header", "payload", "signature"};
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].trim().isEmpty()) {
                throw new JwtException.InvalidFormat("Empty " + partNames[i] + " part");
            }
        }

        return parts;
    }
    @SuppressWarnings("unchecked")
    private Map<String, Object> decodeBase64Json(String base64Part, String partName) {
        try {
            String json = new String(Base64.getUrlDecoder().decode(base64Part));
            return objectMapper.readValue(json, Map.class);
        } catch (IllegalArgumentException e) {
            throw new JwtException.InvalidEncoding("Invalid Base64 encoding in " + partName + ": " + e.getMessage());
        } catch (Exception e) {
            throw new JwtException.InvalidJson("Invalid JSON in " + partName + ": " + e.getMessage());
        }
    }

    private void validateTokenAndSecret(String token, String secret) {
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException.EmptyToken();
        }

        if (secret == null || secret.trim().isEmpty()) {
            throw new JwtException.EmptySecret();
        }
    }

    private Claims parseJwtClaims(String token, String secret) {
        try {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
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
    }

} 