package com.practice.backend.filtering.specification;

import com.practice.backend.filtering.common.FilteringOperation;
import com.practice.backend.filtering.common.SearchCriteria;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class EqualingOrNullSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        final String[] parts = searchCriteria.getKey().split("\\.");
        Path<?> path;
        if (parts.length == 2) {
            path = root.get(parts[0]).get(parts[1]);
        } else {
            path = root.get(searchCriteria.getKey());
        }

        if (searchCriteria.getValue() != null) {
            if (FilteringOperation.EQUAL == searchCriteria.getOperation()) {
                return cb.equal(path, searchCriteria.getValue());
            } else if (FilteringOperation.NOT_EQUAL == searchCriteria.getOperation()) {
                return cb.notEqual(path, searchCriteria.getValue());
            } else {
                return null;
            }
        } else {
            if (FilteringOperation.EQUAL == searchCriteria.getOperation()) {
                return cb.isNull(path);
            } else {
                return cb.isNotNull(path);
            }
        }
    }
}