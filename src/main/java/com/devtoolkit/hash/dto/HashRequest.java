package com.devtoolkit.hash.dto;

import lombok.Data;

@Data
public class HashRequest {
    private String text;
    private String algorithm;
} 