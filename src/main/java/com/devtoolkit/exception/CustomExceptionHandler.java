package com.devtoolkit.exception;

import com.devtoolkit.common.dto.ServiceResponse;
import com.devtoolkit.common.helper.ExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom exception handler for DevToolkit application
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
    private static final String ATR_MATCHING_PATTERN = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern";
    private static final String EXCEPTION = "EXCEPTION";
    private static final String HTTP_EXCEPTION = "HTTP_EXCEPTION";
    private static final String ERROR_CODE_403 = "403";
    private static final String ERROR_CODE_400 = "400";

    @Autowired
    private ExceptionHelper exceptionHelper;

    /**
     * This will handle all the DevToolkitException
     *
     * @param ex
     * @param request
     * @return response as a ResponseEntity
     */
    @ExceptionHandler(DevToolkitException.class)
    public final ResponseEntity<Object> handleDevToolkitException(DevToolkitException ex, WebRequest request) {
        log.error("Exception in handleDevToolkitException for {}", request, ex);
        ServiceResponse<Object> errorResponse = exceptionHelper.prepareErrorResponse(ex);
        return ResponseEntity.status(getHTTPStatusCode(ex)).body(errorResponse);
    }

    /**
     * This will handle all the GenericException
     *
     * @param ex
     * @param request
     * @return response as a ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        log.error("Exception in handleGenericException for {}", request, ex);
        ServiceResponse<Object> errorResponse = exceptionHelper.prepareErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * This will handle all the internal exception
     *
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param request
     * @return response as a ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                           HttpStatusCode status, WebRequest request) {
        log.error("Exception in handleExceptionInternal for {}", request, ex);
        ServiceResponse<Object> errorResponse = exceptionHelper.prepareHTTPErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * This will return Http Status code
     *
     * @param ex
     * @return HttpStatus
     */
    private HttpStatus getHTTPStatusCode(DevToolkitException ex) {
        if (ex.getErrorCode().getCode().contains(ERROR_CODE_403)) {
            return HttpStatus.FORBIDDEN;
        } else if (ex.getErrorCode().getCode().contains(ERROR_CODE_400)) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
} 