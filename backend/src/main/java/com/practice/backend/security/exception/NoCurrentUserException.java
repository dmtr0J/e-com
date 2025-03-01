package com.practice.backend.security.exception;

public class NoCurrentUserException extends RuntimeException {
    public NoCurrentUserException(String message) {
        super(message);
    }
}
