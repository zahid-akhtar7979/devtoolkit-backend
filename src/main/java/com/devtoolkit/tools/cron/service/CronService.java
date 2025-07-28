package com.devtoolkit.tools.cron.service;

import java.util.List;

public interface CronService {
    List<String> getNextExecutions(String cronExpression, int count);
    String getDescription(String cronExpression);
} 