package com.practice.backend.dao.exception;

public class MissingPropertyException extends RuntimeException{

    public MissingPropertyException(String message) {
        super(message);
    }

}
