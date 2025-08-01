package com.devtoolkit.common.helper;

import com.devtoolkit.common.dto.Error;
import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.enums.Status;
import com.devtoolkit.exception.DevToolkitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper component to prepare error responses
 */
@Component
public class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);
    public static final String UNKNOWN_ERROR = "Unknown Error";
    public static final String ERROR_CODE_400 = "DTK-400";
    public static final String ERROR_CODE_500 = "DTK-500";

    @Autowired
    @Qualifier("errorMessageSource")
    private MessageSource errorMessageSource;

    /**
     * This will prepare the error response from DevToolkitException
     *
     * @param ex
     * @return Object
     */
    public ServiceResponse<Object> prepareErrorResponse(DevToolkitException ex) {
        ServiceResponse<Object> response = new ServiceResponse<>();
        response.setStatus(Status.FAIL);
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        if (null == ex.getErrorCode()) {
            ex.setErrorCode(com.devtoolkit.common.enums.ErrorCode.INTERNAL_EXCEPTION);
        }
        error.setErrorCode(ex.getErrorCode().getCode());
        error.setErrorDescription(getErrorDesc(ex));
        errors.add(error);
        response.setErrors(errors);
        return response;
    }

    /**
     * This will prepare the error response from Exception
     *
     * @param ex
     * @return Object
     */
    public ServiceResponse<Object> prepareErrorResponse(Exception ex) {
        ServiceResponse<Object> response = new ServiceResponse<>();
        response.setStatus(Status.FAIL);
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setErrorCode(ERROR_CODE_500);
        error.setErrorDescription(ex.getMessage() == null ? UNKNOWN_ERROR : ex.getMessage());
        errors.add(error);
        response.setErrors(errors);
        return response;
    }

    /**
     * This will prepare the HTTP error response from Exception
     *
     * @param exception
     * @return Object
     */
    public ServiceResponse<Object> prepareHTTPErrorResponse(Exception exception) {
        ServiceResponse<Object> response = new ServiceResponse<>();
        response.setStatus(Status.FAIL);
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setErrorCode(ERROR_CODE_400);
        error.setErrorDescription(exception.getMessage() == null ? UNKNOWN_ERROR : exception.getMessage());
        errors.add(error);
        response.setErrors(errors);
        return response;
    }

    /**
     * This will return the error description
     *
     * @param ex
     * @return errorDescription
     */
    private String getErrorDesc(DevToolkitException ex) {
        String format = "";
        String errorDesc = "";
        try {
            format = errorMessageSource.getMessage(ex.getErrorCode().getCode(), null, null);
            if (format != null && !format.isEmpty()) {
                errorDesc = MessageFormat.format(format, (Object[]) ex.getArguments());
            } else {
                errorDesc = ex.getErrorCode().getDefaultMessage();
            }
        } catch (Exception e) {
            logger.error("Exception while getting Error description", e);
            errorDesc = ex.getErrorCode().getDefaultMessage();
        }
        return errorDesc;
    }

    /**
     * Sets the exception audit fields.
     *
     * @param serviceName the service name
     * @param refKey      the ref key
     * @param pex         the pex
     */
    public void setExceptionAuditFields(String serviceName, String refKey, DevToolkitException pex) {
        pex.setReferenceKey(refKey);
        pex.setServiceName(serviceName);
    }
} 