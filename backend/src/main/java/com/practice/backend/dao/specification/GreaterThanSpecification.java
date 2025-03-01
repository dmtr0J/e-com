package com.practice.backend.dao.specification;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class GreaterThanSpecification<T, U extends Comparable<U>> implements Specification<T> {
    private final String fieldPath;
    private final U value;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Expression<U> searchExpression = root.get(fieldPath);
        return cb.greaterThan(searchExpression, value);
    }
}