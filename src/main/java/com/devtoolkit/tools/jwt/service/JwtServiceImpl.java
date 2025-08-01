package com.devtoolkit.tools.jwt.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.jwt.constants.JwtConstants;
import com.devtoolkit.tools.jwt.dto.JwtResponse;
import com.devtoolkit.tools.jwt.validation.JwtRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    private JwtRequestValidator validator;

    @Override
    public JwtResponse decodeToken(String token) {
        token = normalizeToken(token);
        String[] parts = validateTokenFormat(token);
        JwtResponse response = new JwtResponse();
        try {
            response.setHeader(decodeBase64Json(parts[0], JwtConstants.HEADER_PART));
            response.setPayload(decodeBase64Json(parts[1], JwtConstants.PAYLOAD_PART));
            try {
                Base64.getUrlDecoder().decode(parts[2]);
            } catch (IllegalArgumentException e) {
                response.setSignature(parts[2]);
                throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, e, JwtConstants.INVALID_BASE64_ENCODING_IN_SIGNATURE);
            }
            response.setSignature(parts[2]);
            response.setValid(true);
            response.setMessage(JwtConstants.TOKEN_DECODED_SUCCESS_MESSAGE);
            return response;

        } catch (DevToolkitException e) {
            throw e;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, e, JwtConstants.UNEXPECTED_ERROR_DURING_TOKEN_DECODING);
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
        } catch (DevToolkitException e) {
            throw e;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, e, JwtConstants.UNEXPECTED_ERROR_DURING_VERIFICATION);
        }
    }

    private String normalizeToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, JwtConstants.EMPTY_TOKEN_MESSAGE);
        }
        token = token.trim();
        if (token.toLowerCase().startsWith(JwtConstants.BEARER_PREFIX.toLowerCase())) {
            token = token.substring(7);
        }
        return token;
    }
    private String[] validateTokenFormat(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new DevToolkitException(ErrorCode.JWT_INVALID_FORMAT, JwtConstants.TOKEN_HAS_PARTS_EXPECTED_MESSAGE + parts.length + JwtConstants.PARTS_EXPECTED_3_MESSAGE);
        }
        String[] partNames = {JwtConstants.HEADER_PART, JwtConstants.PAYLOAD_PART, JwtConstants.SIGNATURE_PART};
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].trim().isEmpty()) {
                throw new DevToolkitException(ErrorCode.JWT_INVALID_FORMAT, JwtConstants.EMPTY_PART_MESSAGE + partNames[i] + JwtConstants.PART_MESSAGE);
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
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, e, JwtConstants.INVALID_BASE64_ENCODING_IN_MESSAGE + partName);
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, e, JwtConstants.INVALID_JSON_IN_MESSAGE + partName);
        }
    }

    private void validateTokenAndSecret(String token, String secret) {
        if (token == null || token.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.JWT_VALIDATION_ERROR, JwtConstants.EMPTY_TOKEN_MESSAGE);
        }

        if (secret == null || secret.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.JWT_INVALID_SECRET, JwtConstants.EMPTY_SECRET_MESSAGE);
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
            throw new DevToolkitException(ErrorCode.JWT_TOKEN_EXPIRED, e, JwtConstants.JWT_TOKEN_EXPIRED_MESSAGE);
        } catch (UnsupportedJwtException e) {
            throw new DevToolkitException(ErrorCode.JWT_UNSUPPORTED_FORMAT, e, JwtConstants.UNSUPPORTED_JWT_TOKEN_FORMAT_MESSAGE);
        } catch (MalformedJwtException e) {
            throw new DevToolkitException(ErrorCode.JWT_MALFORMED_TOKEN, e, JwtConstants.MALFORMED_JWT_TOKEN_MESSAGE);
        } catch (SignatureException e) {
            throw new DevToolkitException(ErrorCode.JWT_INVALID_SIGNATURE, e, JwtConstants.INVALID_JWT_SIGNATURE_MESSAGE);
        } catch (IllegalArgumentException e) {
            throw new DevToolkitException(ErrorCode.JWT_INVALID_FORMAT, e, JwtConstants.INVALID_JWT_TOKEN_MESSAGE);
        }
    }

} 