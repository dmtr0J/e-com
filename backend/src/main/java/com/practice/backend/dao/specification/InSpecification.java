package com.practice.backend.dao.specification;

import com.practice.backend.model.Identifiable;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Collection;

@AllArgsConstructor
public class InSpecification<T extends Identifiable, U extends Serializable> implements Specification<T> {
    private final String fieldPath;
    private final Collection<U> values;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> cq, @NonNull CriteriaBuilder cb) {
        Path<T> path = SpecificationUtil.buildPath(root, fieldPath);
        return path.in(values);
    }

}