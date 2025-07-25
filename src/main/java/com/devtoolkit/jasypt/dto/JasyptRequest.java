package com.devtoolkit.jasypt.dto;

import lombok.Data;

@Data
public class JasyptRequest {
    private String text;
    private String password;
    private String algorithm;
} 