package com.devtoolkit.tools.hash.service;

import java.util.Map;

public interface HashService {
    Map<String, String> generateHash(String text);
    String generateHash(String text, String algorithm);
} 