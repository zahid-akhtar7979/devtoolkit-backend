package com.devtoolkit.cron.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CronServiceImpl implements CronService {
    
    @Override
    public List<String> getNextExecutions(String cronExpression, int count) {
        List<String> executions = new ArrayList<>();
        
        try {
            // Simple CRON parser for common patterns
            String[] parts = cronExpression.split("\\s+");
            if (parts.length < 5) {
                throw new IllegalArgumentException("Invalid CRON expression format");
            }
            
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            for (int i = 0; i < count; i++) {
                LocalDateTime next = getNextExecution(parts, now.plusMinutes(i));
                executions.add(next.format(formatter));
            }
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CRON expression: " + e.getMessage());
        }
        
        return executions;
    }
    
    private LocalDateTime getNextExecution(String[] parts, LocalDateTime from) {
        // This is a simplified implementation
        // In a real application, you'd use a library like Quartz or cron-utils
        LocalDateTime result = from;
        
        // For demonstration, we'll just add minutes based on the minute field
        String minuteField = parts[0];
        if (!minuteField.equals("*")) {
            if (minuteField.contains("/")) {
                String[] div = minuteField.split("/");
                int interval = Integer.parseInt(div[1]);
                result = result.plusMinutes(interval);
            } else if (minuteField.contains(",")) {
                // Handle comma-separated values
                result = result.plusMinutes(1);
            } else {
                result = result.plusMinutes(1);
            }
        } else {
            result = result.plusMinutes(1);
        }
        
        return result;
    }
    
    @Override
    public String getDescription(String cronExpression) {
        try {
            String[] parts = cronExpression.split("\\s+");
            if (parts.length < 5) {
                return "Invalid CRON expression";
            }
            
            StringBuilder description = new StringBuilder();
            description.append("Every ");
            
            // Minute
            if (parts[0].equals("*")) {
                description.append("minute");
            } else if (parts[0].contains("/")) {
                String[] div = parts[0].split("/");
                description.append(div[1]).append(" minutes");
            } else {
                description.append("minute ").append(parts[0]);
            }
            
            // Hour
            if (!parts[1].equals("*")) {
                description.append(" at hour ").append(parts[1]);
            }
            
            // Day of month
            if (!parts[2].equals("*")) {
                description.append(" on day ").append(parts[2]);
            }
            
            // Month
            if (!parts[3].equals("*")) {
                description.append(" in month ").append(parts[3]);
            }
            
            // Day of week
            if (!parts[4].equals("*")) {
                description.append(" on ").append(getDayName(parts[4]));
            }
            
            return description.toString();
            
        } catch (Exception e) {
            return "Invalid CRON expression";
        }
    }
    
    private String getDayName(String day) {
        switch (day) {
            case "0": case "7": return "Sunday";
            case "1": return "Monday";
            case "2": return "Tuesday";
            case "3": return "Wednesday";
            case "4": return "Thursday";
            case "5": return "Friday";
            case "6": return "Saturday";
            default: return day;
        }
    }
} 