package com.practice.backend.filtering.exception;

import lombok.Getter;

@Getter
public class IllegalFilteringOperationException extends RuntimeException {
    private String operation;

    public IllegalFilteringOperationException(String message) {
        super(message);
    }

    public IllegalFilteringOperationException(String operation, String message) {
        super(message);
        this.operation = operation;
    }
}