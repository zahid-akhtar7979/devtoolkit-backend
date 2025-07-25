package com.devtoolkit.jwt.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String token;
    private String secret;
} 