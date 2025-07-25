package com.devtoolkit.jwt.service;

import java.util.Map;

public interface JwtService {
    Map<String, Object> decodeToken(String token);
    boolean verifyToken(String token, String secret);
} 