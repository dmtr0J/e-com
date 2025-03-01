package com.practice.backend.api.v1.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import java.util.Set;

public class PatchFieldConstraintViolationException extends ConstraintViolationException {

    public PatchFieldConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }

    public PatchFieldConstraintViolationException(String field, String message) {
        super(Set.of(ConstraintViolationImpl
                .forBeanValidation(null, null, null, message, null, null, null, null,
                        PathImpl.createPathFromString(field), null, null)));
    }

}
