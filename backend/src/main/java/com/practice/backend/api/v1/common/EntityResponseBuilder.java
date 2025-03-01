package com.practice.backend.api.v1.common;

import com.practice.backend.model.Identifiable;

import java.util.List;

public interface EntityResponseBuilder<T extends Identifiable, R extends ApiResponse> {

    R convertEntityToResponse(T entity);

    R convertEntityToResponse(T entity, List<String> entitiesToExpand);
}

