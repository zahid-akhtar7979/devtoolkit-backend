package com.devtoolkit.tools.cron.dto;

import lombok.Data;
import java.util.List;

/**
 * Response DTO for Cron operations
 */
@Data
public class CronResponse {
    private String cronExpression;
    private List<String> nextExecutions;
    private String description;
    private boolean valid;
    private String message;
} 