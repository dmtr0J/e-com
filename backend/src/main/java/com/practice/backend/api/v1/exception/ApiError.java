package com.practice.backend.api.v1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
public class ApiError {

    private final HttpStatus status;

    private final String errorClass;

    private final String title;

    public ApiError(Throwable exception, HttpStatus status) {
        this.status = status;
        final Throwable ex = Objects.requireNonNull(exception);
        title = ex.getMessage();
        errorClass = ex.getClass().getSimpleName();
    }
}

