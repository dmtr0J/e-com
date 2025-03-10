package com.practice.backend.api.v1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String title;
    private String errorClass;
    private List<String> errors;

    public ApiError(HttpStatus status, Throwable exception, List<String> errors) {
        this.status = status;
        this.title = exception.getMessage();
        this.errorClass = exception.getClass().getSimpleName();
        this.errors = errors;
    }

    public ApiError(HttpStatus status, Throwable exception, String error) {
        this(status, exception, Collections.singletonList(error));
    }

}

