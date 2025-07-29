package com.devtoolkit.tools.base64.service;

import com.devtoolkit.tools.base64.dto.Base64Response;

public interface Base64Service {
    Base64Response encode(String text);
    Base64Response decode(String text);
} 