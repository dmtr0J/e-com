package com.practice.backend.api.v1.common;

import com.practice.backend.api.v1.exception.ObjectNotFoundException;
import com.practice.backend.dao.exception.MissingPropertyException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@NoArgsConstructor
public final class BaseControllerUtil {

    public static <T, ID> T getObjectOrNotFound(T obj, ID id, Class<T> relClass) {
        if (obj == null) {
            throw new ObjectNotFoundException("Object with id " + id + " and class " + relClass.getSimpleName() + " not found");
        }
        return obj;
    }

    public static <T, ID> T getObjectOrNotFound(T obj, ID id, Class<T> relClass, Class<?> onClass) {
        if (obj == null) {
            final String message = String.format("Failed to link %s (ID=%s) on %s", relClass.getSimpleName(), id, onClass.getSimpleName());
            log.warn(message);
            throw new ObjectNotFoundException(message);
        }
        return obj;
    }

    public static List<String> parseExpandField(Optional<String> expandFields) {
        return expandFields.map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList());
    }

    public static void checkPatchRequest(Map<String, Object> request) {
        if (request.isEmpty()) {
            throw new MissingPropertyException("Request object on PATCH is empty.");
        }
    }
}
