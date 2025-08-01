package com.devtoolkit.exception;

import com.devtoolkit.common.enums.ErrorCode;

/**
 * Custom exception class for DevToolkit application
 */
public class DevToolkitException extends RuntimeException {

    private static final long serialVersionUID = 8323084147015186649L;
    private static final String UNKNOWN = "UNKNOWN";

    private ErrorCode errorCode;
    private String referenceKey;
    private String serviceName;
    private String[] arguments;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public DevToolkitException(ErrorCode errorCode, String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public DevToolkitException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DevToolkitException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
    
    public DevToolkitException(Throwable cause) {
        super(cause);
    }
    
    public DevToolkitException(ErrorCode errorCode, Throwable cause, String... arguments) {
        super(cause);
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    public DevToolkitException(ErrorCode errorCode, String... arguments) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    public String getReferenceKey() {
        return referenceKey == null ? UNKNOWN : referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
} 