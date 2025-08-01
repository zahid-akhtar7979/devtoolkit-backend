package com.devtoolkit.common.dto;

import com.devtoolkit.common.enums.Status;
import java.util.List;

/**
 * DTO class to represent standardized API responses
 */
public class ServiceResponse<T> {
    
    private Status status;
    private T data;
    private List<Error> errors;
    
    public ServiceResponse() {}
    
    public ServiceResponse(Status status, T data) {
        this.status = status;
        this.data = data;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public List<Error> getErrors() {
        return errors;
    }
    
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
} 