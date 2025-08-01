package com.devtoolkit.common.enums;

/**
 * Enum to define response status values
 */
public enum Status {
    SUCCESS("SUCCESS"),
    FAIL("FAIL");
    
    private final String value;
    
    Status(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
} 