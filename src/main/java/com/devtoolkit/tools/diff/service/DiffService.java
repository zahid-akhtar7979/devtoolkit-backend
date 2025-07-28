package com.devtoolkit.tools.diff.service;

import com.devtoolkit.tools.diff.dto.DiffRequest;
import java.util.Map;

public interface DiffService {
    Map<String, Object> compareText(String text1, String text2);
    Map<String, Object> generateEnhancedDiff(DiffRequest request);
} 