package com.practice.backend.dao.specification;

import com.practice.backend.model.Identifiable;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class NotEqualSpecification<T extends Identifiable> implements Specification<T> {
    private final String fieldPath;
    private final Object value;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<T> path = SpecificationUtil.buildPath(root, fieldPath);
        return cb.notEqual(path, value);
    }

}