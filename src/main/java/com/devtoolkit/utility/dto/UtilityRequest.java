package com.devtoolkit.utility.dto;

import lombok.Data;

@Data
public class UtilityRequest {
    private String text;
    private String type;
    private String format;
    private String sourceFormat;
    private String targetFormat;
    private String url;
    private String method;
    private String headers;
    private String body;
    private String text1;
    private String text2;
    private String sql;
    private String dialect;
    private Integer count;
} 