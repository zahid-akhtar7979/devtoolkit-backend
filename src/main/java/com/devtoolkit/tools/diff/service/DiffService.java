package com.devtoolkit.tools.diff.service;

import com.devtoolkit.tools.diff.dto.DiffRequest;
import com.devtoolkit.tools.diff.dto.DiffResponse;

public interface DiffService {
    DiffResponse compareText(String text1, String text2);
    DiffResponse generateEnhancedDiff(DiffRequest request);
} 