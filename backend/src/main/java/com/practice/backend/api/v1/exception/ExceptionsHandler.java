package com.practice.backend.api.v1.exception;

import com.practice.backend.dao.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
//TODO: Must be used only for controller
/*@ControllerAdvice(basePackages = {
        "com.practice.backend.controller"
})*/
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    //TODO: Maybe make abstract advice and then create one for needed layer
    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        final ApiError apiError = buildErrorOccurred(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(ex, apiError, request);
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    protected ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {
        final ApiError apiError = buildErrorOccurred(ex, HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, apiError, request);
    }

    private ApiError buildErrorOccurred(Exception ex, HttpStatus status) {
        final String defaultDescription = String.format("Error occurred. %s", ex.getMessage());
        return new ApiError(status, ex.getClass().getSimpleName(), defaultDescription);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, ApiError error, WebRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return handleExceptionInternal(ex, error, headers, error.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.warn("Exception occurred:", ex);
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
