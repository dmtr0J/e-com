package com.practice.backend.dao.specification;

import com.practice.backend.model.Identifiable;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@AllArgsConstructor
public class InCollectionSpecification<T, U extends Identifiable> implements Specification<T> {
    private final Class<T> entityClass;
    private final String fieldPath;
    private final U value;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Subquery<T> subQuery = Objects.requireNonNull(query).subquery(entityClass);
        Root<T> sqRoot = subQuery.from(entityClass);
        Join<U, T> sqJoin = SpecificationUtil.buildJoin(sqRoot, fieldPath);
        subQuery.select(sqRoot);
        subQuery.where(cb.equal(sqJoin, value));

        return cb.in(root).value(subQuery);
    }
}