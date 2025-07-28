package com.devtoolkit.tools.cron.dto;

import lombok.Data;

@Data
public class CronRequest {
    private String cronExpression;
} 