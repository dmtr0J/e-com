package com.practice.backend.filtering.specification;

import com.practice.backend.filtering.common.SearchCriteria;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@AllArgsConstructor
public class LocalDateComparisonSpecification<EntityType> implements Specification<EntityType> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<EntityType> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {

        LocalDate value = LocalDate.parse(searchCriteria.getValue().toString());
        Expression<LocalDate> searchExpression = root.get(searchCriteria.getKey());

        return switch (searchCriteria.getOperation()) {
            case EQUAL -> cb.equal(searchExpression, value);
            case NOT_EQUAL -> cb.notEqual(searchExpression, value);
            case GREATER_THEN -> cb.greaterThan(searchExpression, value);
            case LESS_THEN -> cb.lessThan(searchExpression, value);
            case GREATER_OR_EQUAL -> cb.greaterThanOrEqualTo(searchExpression, value);
            case LESS_OR_EQUAL -> cb.lessThanOrEqualTo(searchExpression, value);
            default -> null;
        };
    }
}
