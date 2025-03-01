package com.practice.backend.dao.specification;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class EqualSpecification<T> implements Specification<T> {

    private final String fieldPath;
    private final Object value;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<T> path = SpecificationUtil.buildPath(root, fieldPath);
        return cb.equal(path, value);
    }
}

