package com.devtoolkit.tools.cron.service;

import com.devtoolkit.tools.cron.dto.CronResponse;

public interface CronService {
    CronResponse evaluateCron(String cronExpression, int count);
} 