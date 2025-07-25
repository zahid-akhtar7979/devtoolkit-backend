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
            
            LocalDateTime next = getNextExecution(parts, now);
            for (int i = 0; i < count; i++) {
                executions.add(next.format(formatter));
                next = getNextExecution(parts, next.plusSeconds(1)); // Ensure we get the next occurrence
            }
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CRON expression: " + e.getMessage());
        }
        
        return executions;
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
    
    @Override
    public String getDescription(String cronExpression) {
        try {
            String[] parts = cronExpression.split("\\s+");
            if (parts.length < 5) {
                return "Invalid CRON expression";
            }
            
            String minute = parts[0];
            String hour = parts[1];
            String dayOfMonth = parts[2];
            String month = parts[3];
            String dayOfWeek = parts[4];
            
            // Handle specific time patterns
            if (!minute.equals("*") && !hour.equals("*")) {
                StringBuilder time = new StringBuilder();
                time.append("At ");
                
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
                        time.append("s");
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
                description.append("Every minute");
            } else if (minute.contains("/") && hour.equals("*")) {
                String[] div = minute.split("/");
                int interval = Integer.parseInt(div[1]);
                description.append("Every ").append(interval).append(" minutes");
            } else if (minute.equals("0") && hour.contains("/")) {
                String[] div = hour.split("/");
                int interval = Integer.parseInt(div[1]);
                description.append("Every ").append(interval).append(" hours");
            } else if (minute.equals("*") && !hour.equals("*")) {
                description.append("Every minute during hour ").append(hour);
            } else {
                // Fallback for complex patterns
                description.append("At minute ").append(minute);
                if (!hour.equals("*")) {
                    description.append(" past hour ").append(hour);
                }
            }
            
            // Add day/month constraints for interval patterns
            if (description.toString().startsWith("Every") || description.toString().startsWith("At minute")) {
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
            return "Invalid CRON expression: " + e.getMessage();
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
    
    private String getMonthName(String month) {
        switch (month) {
            case "1": return "January";
            case "2": return "February";
            case "3": return "March";
            case "4": return "April";
            case "5": return "May";
            case "6": return "June";
            case "7": return "July";
            case "8": return "August";
            case "9": return "September";
            case "10": return "October";
            case "11": return "November";
            case "12": return "December";
            default: return "month " + month;
        }
    }
} 