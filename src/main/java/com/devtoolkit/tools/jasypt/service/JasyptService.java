package com.devtoolkit.tools.jasypt.service;

public interface JasyptService {
    String encrypt(String text, String password, String algorithm);
    String decrypt(String text, String password, String algorithm);
} 