package com.practice.backend.filtering.specification;

import com.practice.backend.filtering.common.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class EqualingSpecification<EntityType> implements Specification<EntityType> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<EntityType> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        return switch (searchCriteria.getOperation()) {
            case EQUAL -> cb.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case NOT_EQUAL -> cb.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            default -> null;
        };
    }
}