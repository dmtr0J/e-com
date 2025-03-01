package com.practice.backend.dao.specification;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@AllArgsConstructor
public class SubclassEqualSpecification<T, U extends T> implements Specification<T> {
    private final Class<U> subclassClass;
    private final String fieldPath;
    private final Object value;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Subquery<U> subQuery = Objects.requireNonNull(query).subquery(subclassClass);
        Root<U> sqRoot = subQuery.from(subclassClass);
        subQuery.select(sqRoot);
        Path<U> path = SpecificationUtil.buildPath(sqRoot, fieldPath);
        subQuery.where(cb.equal(path, value));

        return cb.in(root).value(subQuery);
    }

}
