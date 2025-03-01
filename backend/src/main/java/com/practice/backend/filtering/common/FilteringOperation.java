package com.practice.backend.filtering.common;

import com.practice.backend.filtering.exception.IllegalFilteringOperationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FilteringOperation {
    EQUAL("="),
    NOT_EQUAL("!="),

    CONTAIN(":"),

    GREATER_THEN(">"),
    GREATER_OR_EQUAL(">="),

    LESS_THEN("<"),
    LESS_OR_EQUAL("<="),

    IN("_="),
    NOT_IN("!_="),

    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL");

    private final String code;

    public static FilteringOperation fromString(String text) {
        for (FilteringOperation operation : FilteringOperation.values()) {
            if (operation.code.equalsIgnoreCase(text)) {
                return operation;
            }
        }
        throw new IllegalFilteringOperationException("Unknown filtering operation '" + text + "'");
    }
}
