package com.devtoolkit.tools.hash.service;

import com.devtoolkit.tools.hash.dto.HashResponse;

public interface HashService {
    HashResponse generateHash(String text);
    HashResponse generateHash(String text, String algorithm);
} 