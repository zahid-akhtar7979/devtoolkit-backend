package com.devtoolkit.tools.jasypt.service;

import com.devtoolkit.tools.jasypt.dto.JasyptResponse;

public interface JasyptService {
    JasyptResponse encrypt(String text, String password, String algorithm);
    JasyptResponse decrypt(String text, String password, String algorithm);
} 