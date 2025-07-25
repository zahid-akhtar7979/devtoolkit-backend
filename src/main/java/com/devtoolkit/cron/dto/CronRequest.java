package com.devtoolkit.cron.dto;

import lombok.Data;

@Data
public class CronRequest {
    private String cronExpression;
} 