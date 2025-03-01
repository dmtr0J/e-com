package com.practice.backend.dao.specification;

import com.practice.backend.model.Identifiable;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class NullSpecification<T extends Identifiable> implements Specification<T> {
    private final String fieldPath;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<T> path = SpecificationUtil.buildPath(root, fieldPath);
        return cb.isNull(path);
    }

}
