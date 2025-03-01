package com.practice.backend.filtering.specification;

import com.practice.backend.filtering.common.SearchCriteria;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

@AllArgsConstructor
public class BigDecimalComparisonSpecification<EntityType> implements Specification<EntityType> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<EntityType> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        BigDecimal value = (BigDecimal) searchCriteria.getValue();
        Expression<BigDecimal> searchExpression = root.get(searchCriteria.getKey());

        return switch (searchCriteria.getOperation()) {
            case GREATER_THEN -> cb.greaterThan(searchExpression, value);
            case LESS_THEN -> cb.lessThan(searchExpression, value);
            case GREATER_OR_EQUAL -> cb.greaterThanOrEqualTo(searchExpression, value);
            case LESS_OR_EQUAL -> cb.lessThanOrEqualTo(searchExpression, value);
            case EQUAL -> cb.equal(searchExpression, value);
            case NOT_EQUAL -> cb.notEqual(searchExpression, value);
            default -> null;
        };
    }

}

