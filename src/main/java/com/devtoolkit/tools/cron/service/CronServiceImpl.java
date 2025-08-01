package com.devtoolkit.tools.cron.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.cron.constants.CronConstants;
import com.devtoolkit.tools.cron.dto.CronResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CronServiceImpl implements CronService {
    
    @Override
    public CronResponse evaluateCron(String cronExpression, int count) {
        List<String> executions = new ArrayList<>();
        
        try {
            // Simple CRON parser for common patterns
            String[] parts = cronExpression.split("\\s+");
            if (parts.length < 5) {
                throw new DevToolkitException(ErrorCode.CRON_INVALID_EXPRESSION, CronConstants.INVALID_EXPRESSION_MESSAGE);
            }
            
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CronConstants.DEFAULT_DATE_FORMAT);
            
            LocalDateTime next = getNextExecution(parts, now);
            for (int i = 0; i < count; i++) {
                executions.add(next.format(formatter));
                next = getNextExecution(parts, next.plusSeconds(1)); // Ensure we get the next occurrence
            }
            
            String description = generateDescription(parts);
            
            CronResponse response = new CronResponse();
            response.setCronExpression(cronExpression);
            response.setNextExecutions(executions);
            response.setDescription(description);
            response.setValid(true);
            response.setMessage(CronConstants.EVALUATE_SUCCESS_MESSAGE);
            
            return response;
            
        } catch (DevToolkitException e) {
            throw e;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.CRON_PARSE_ERROR, e, cronExpression);
        }
    }
    
    private LocalDateTime getNextExecution(String[] parts, LocalDateTime from) {
        // This is a simplified implementation for common patterns
        // In a real application, you'd use a library like Quartz or cron-utils
        
        String minute = parts[0];
        String hour = parts[1];
        String dayOfMonth = parts[2];
        String month = parts[3];
        String dayOfWeek = parts[4];
        
        LocalDateTime result = from;
        
        // Handle specific time (minute and hour both specified)
        if (!minute.equals("*") && !hour.equals("*")) {
            int targetMinute = Integer.parseInt(minute);
            int targetHour = Integer.parseInt(hour);
            
            // Set to target time
            result = result.withMinute(targetMinute).withSecond(0).withNano(0);
            
            // If target hour is different, set it
            if (result.getHour() != targetHour) {
                result = result.withHour(targetHour);
            }
            
            // If the time has passed today, move to next day
            if (result.isBefore(from) || result.equals(from)) {
                result = result.plusDays(1);
            }
            
            return result;
        }
        
        // Handle every minute pattern
        if (minute.equals("*") && hour.equals("*")) {
            return from.plusMinutes(1).withSecond(0).withNano(0);
        }
        
        // Handle interval patterns
        if (minute.contains("/")) {
            String[] div = minute.split("/");
            int interval = Integer.parseInt(div[1]);
            return from.plusMinutes(interval).withSecond(0).withNano(0);
        }
        
        if (hour.contains("/") && minute.equals("0")) {
            String[] div = hour.split("/");
            int interval = Integer.parseInt(div[1]);
            return from.plusHours(interval).withMinute(0).withSecond(0).withNano(0);
        }
        
        // Default fallback
        return from.plusMinutes(1).withSecond(0).withNano(0);
    }
    
    private String generateDescription(String[] parts) {
        try {
            if (parts.length < 5) {
                return CronConstants.INVALID_CRON_EXPRESSION;
            }
            
            String minute = parts[0];
            String hour = parts[1];
            String dayOfMonth = parts[2];
            String month = parts[3];
            String dayOfWeek = parts[4];
            
            // Handle specific time patterns
            if (!minute.equals("*") && !hour.equals("*")) {
                StringBuilder time = new StringBuilder();
                time.append(CronConstants.TIME_AT_PREFIX);
                
                // Format time
                int hourInt = Integer.parseInt(hour);
                int minuteInt = Integer.parseInt(minute);
                
                if (hourInt == 0) {
                    time.append(String.format("12:%02d AM", minuteInt));
                } else if (hourInt < 12) {
                    time.append(String.format("%d:%02d AM", hourInt, minuteInt));
                } else if (hourInt == 12) {
                    time.append(String.format("12:%02d PM", minuteInt));
                } else {
                    time.append(String.format("%d:%02d PM", hourInt - 12, minuteInt));
                }
                
                // Add frequency
                if (dayOfMonth.equals("*") && month.equals("*") && dayOfWeek.equals("*")) {
                    time.append(" every day");
                } else if (!dayOfWeek.equals("*")) {
                    time.append(" on ").append(getDayName(dayOfWeek));
                    if (!dayOfMonth.equals("*") || !month.equals("*")) {
                        time.append(CronConstants.TIME_SECONDS_SUFFIX);
                    }
                } else if (!dayOfMonth.equals("*")) {
                    time.append(" on day ").append(dayOfMonth).append(" of the month");
                }
                
                if (!month.equals("*")) {
                    time.append(" in ").append(getMonthName(month));
                }
                
                return time.toString();
            }
            
            // Handle interval patterns
            StringBuilder description = new StringBuilder();
            
            if (minute.equals("*") && hour.equals("*")) {
                description.append(CronConstants.TIME_EVERY_MINUTE);
            } else if (minute.contains("/") && hour.equals("*")) {
                String[] div = minute.split("/");
                int interval = Integer.parseInt(div[1]);
                description.append(CronConstants.TIME_EVERY_MINUTES_PREFIX).append(interval).append(CronConstants.TIME_MINUTES_SUFFIX);
            } else if (minute.equals("0") && hour.contains("/")) {
                String[] div = hour.split("/");
                int interval = Integer.parseInt(div[1]);
                description.append(CronConstants.TIME_EVERY_MINUTES_PREFIX).append(interval).append(CronConstants.TIME_HOURS_SUFFIX);
            } else if (minute.equals("*") && !hour.equals("*")) {
                description.append(CronConstants.TIME_EVERY_MINUTE_DURING_HOUR).append(hour);
            } else {
                // Fallback for complex patterns
                description.append(CronConstants.TIME_AT_MINUTE).append(minute);
                if (!hour.equals("*")) {
                    description.append(" past hour ").append(hour);
                }
            }
            
            // Add day/month constraints for interval patterns
            if (description.toString().startsWith(CronConstants.TIME_EVERY_MINUTES_PREFIX) || description.toString().startsWith(CronConstants.TIME_AT_MINUTE)) {
                if (!dayOfMonth.equals("*")) {
                    description.append(" on day ").append(dayOfMonth);
                }
                if (!month.equals("*")) {
                    description.append(" in ").append(getMonthName(month));
                }
                if (!dayOfWeek.equals("*")) {
                    description.append(" on ").append(getDayName(dayOfWeek));
                }
            }
            
            return description.toString();
            
        } catch (Exception e) {
            return CronConstants.INVALID_CRON_EXPRESSION;
        }
    }
    

    
    private String getDayName(String day) {
        switch (day) {
            case "0": case "7": return CronConstants.DAY_SUNDAY;
            case "1": return CronConstants.DAY_MONDAY;
            case "2": return CronConstants.DAY_TUESDAY;
            case "3": return CronConstants.DAY_WEDNESDAY;
            case "4": return CronConstants.DAY_THURSDAY;
            case "5": return CronConstants.DAY_FRIDAY;
            case "6": return CronConstants.DAY_SATURDAY;
            default: return day;
        }
    }
    
    private String getMonthName(String month) {
        switch (month) {
            case "1": return CronConstants.MONTH_JANUARY;
            case "2": return CronConstants.MONTH_FEBRUARY;
            case "3": return CronConstants.MONTH_MARCH;
            case "4": return CronConstants.MONTH_APRIL;
            case "5": return CronConstants.MONTH_MAY;
            case "6": return CronConstants.MONTH_JUNE;
            case "7": return CronConstants.MONTH_JULY;
            case "8": return CronConstants.MONTH_AUGUST;
            case "9": return CronConstants.MONTH_SEPTEMBER;
            case "10": return CronConstants.MONTH_OCTOBER;
            case "11": return CronConstants.MONTH_NOVEMBER;
            case "12": return CronConstants.MONTH_DECEMBER;
            default: return CronConstants.MONTH_PREFIX + month;
        }
    }
} 