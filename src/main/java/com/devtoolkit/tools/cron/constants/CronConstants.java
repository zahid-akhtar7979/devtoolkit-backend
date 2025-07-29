package com.devtoolkit.tools.cron.constants;

/**
 * Constants for Cron operations
 */
public final class CronConstants {
    
    private CronConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Operations
    public static final String EVALUATE_OPERATION = "evaluate";
    
    // Error messages
    public static final String EMPTY_EXPRESSION_MESSAGE = "Cron expression cannot be null or empty";
    public static final String INVALID_EXPRESSION_MESSAGE = "Invalid CRON expression format";
    public static final String EVALUATE_SUCCESS_MESSAGE = "Cron expression evaluated successfully";
    
    // Default values
    public static final int DEFAULT_EXECUTION_COUNT = 5;
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // Time descriptions
    public static final String TIME_AT_PREFIX = "At ";
    public static final String TIME_SECONDS_SUFFIX = "s";
    public static final String TIME_EVERY_MINUTE = "Every minute";
    public static final String TIME_EVERY_MINUTES_PREFIX = "Every ";
    public static final String TIME_MINUTES_SUFFIX = " minutes";
    public static final String TIME_HOURS_SUFFIX = " hours";
    public static final String TIME_EVERY_MINUTE_DURING_HOUR = "Every minute during hour ";
    public static final String TIME_AT_MINUTE = "At minute ";
    
    // Day names
    public static final String DAY_SUNDAY = "Sunday";
    public static final String DAY_MONDAY = "Monday";
    public static final String DAY_TUESDAY = "Tuesday";
    public static final String DAY_WEDNESDAY = "Wednesday";
    public static final String DAY_THURSDAY = "Thursday";
    public static final String DAY_FRIDAY = "Friday";
    public static final String DAY_SATURDAY = "Saturday";
    
    // Month names
    public static final String MONTH_JANUARY = "January";
    public static final String MONTH_FEBRUARY = "February";
    public static final String MONTH_MARCH = "March";
    public static final String MONTH_APRIL = "April";
    public static final String MONTH_MAY = "May";
    public static final String MONTH_JUNE = "June";
    public static final String MONTH_JULY = "July";
    public static final String MONTH_AUGUST = "August";
    public static final String MONTH_SEPTEMBER = "September";
    public static final String MONTH_OCTOBER = "October";
    public static final String MONTH_NOVEMBER = "November";
    public static final String MONTH_DECEMBER = "December";
    public static final String MONTH_PREFIX = "month ";
    
    // Error messages
    public static final String INVALID_CRON_EXPRESSION = "Invalid CRON expression";
} 