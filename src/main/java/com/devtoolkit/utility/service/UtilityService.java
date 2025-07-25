package com.devtoolkit.utility.service;

import java.util.Map;

public interface UtilityService {
    String encodeUrl(String url);
    String decodeUrl(String url);
    String generateUuid(String type);
    String convertTimestamp(String timestamp, String format);
    String convertFormat(String text, String sourceFormat, String targetFormat);
    Map<String, Object> compareText(String text1, String text2);
    String generateCurl(String url, String method, String headers, String body);
    String formatSql(String sql, String dialect);
    String testRegex(String pattern, String text);
} 