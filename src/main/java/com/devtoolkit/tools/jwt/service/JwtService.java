package com.devtoolkit.tools.jwt.service;

import com.devtoolkit.tools.jwt.dto.JwtResponse;

public interface JwtService {
    JwtResponse decodeToken(String token);
    boolean verifyToken(String token, String secret);
} 