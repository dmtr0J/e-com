package com.practice.backend.api.v1.exception;

import com.practice.backend.dao.exception.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle {@link MethodArgumentNotValidException}.
     * This exception is thrown when an argument annotated with @Valid failed validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex, errors);
        return this.handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.getMethod());
        sb.append(" method is not supported for this request.");
        if (ex.getSupportedHttpMethods() == null || ex.getSupportedHttpMethods().isEmpty() ) {
            ApiError apiError = new ApiError(
                    HttpStatus.METHOD_NOT_ALLOWED, ex, sb.toString());
            return this.handleExceptionInternal(ex, apiError, request);
        } else {
            sb.append(" Supported methods are ");
            ex.getSupportedHttpMethods()
                    .forEach(method -> sb.append(method).append(" "));
            ApiError apiError = new ApiError(
                    HttpStatus.METHOD_NOT_ALLOWED, ex, sb.toString());
            return this.handleExceptionInternal(ex, apiError, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex, error);
        return this.handleExceptionInternal(ex, apiError, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String error = ex.getRequestPartName() + " part is missing";
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex, error);
        return this.handleExceptionInternal(ex, apiError, request);
    }

    /**
     * Handle {@link ConstraintViolationException}.
     * This method is triggered when a request violates a constraint
     * (@NotNull, @Size, @Email...) or database constraints (unique...).
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex, errors);
        return this.handleExceptionInternal(ex, apiError, request);
    }

    /**
     * Handle {@link MethodArgumentTypeMismatchException}.
     * This exception is thrown when a method argument is of the wrong type.
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        String error;
        if (ex.getRequiredType() == null) {
            error = "Invalid type for parameter: " + ex.getName();
        } else {
            error = ex.getName() + " should be of type " +
                    ex.getRequiredType().getName();
        }
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex, error);
        return this.handleExceptionInternal(ex, apiError, request);
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<Object> handleAuthenticationException(
            AuthenticationException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED, ex, "Authentication failed");
        return this.handleExceptionInternal(ex, apiError, request);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAuthorizationException(
            AccessDeniedException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.FORBIDDEN, ex, "No permission to access this resource");
        return this.handleExceptionInternal(ex, apiError, request);
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            jakarta.persistence.EntityNotFoundException.class,
            ObjectNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFoundExceptions(
            Exception ex, WebRequest request) {
        ApiError apiError = this.buildErrorOccurred(
                ex, HttpStatus.NOT_FOUND);
        return this.handleExceptionInternal(ex, apiError, request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> defaultExceptionHandler(
            Exception ex, WebRequest request) {
        ApiError apiError = this.buildErrorOccurred(
                ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return this.handleExceptionInternal(ex, apiError, request);
    }

    private ApiError buildErrorOccurred(
            Exception ex, HttpStatus status) {
        return new ApiError(status, ex, "error occurred");
    }

    private ResponseEntity<Object> handleExceptionInternal(
            Exception ex, ApiError error, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return handleExceptionInternal(ex, error, headers, error.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        logger.warn("Exception occurred:", ex);
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
