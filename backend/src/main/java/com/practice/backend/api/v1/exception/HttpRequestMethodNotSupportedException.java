package com.practice.backend.api.v1.exception;

public class HttpRequestMethodNotSupportedException extends RuntimeException {

    public HttpRequestMethodNotSupportedException(String message) {
        super(message);
    }
}