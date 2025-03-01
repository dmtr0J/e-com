package com.practice.backend.filtering.specification;

import com.practice.backend.filtering.common.SearchCriteria;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class StringComparisonSpecification<EntityType> implements Specification<EntityType> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<EntityType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String searchValueLowerCase = StringUtils.lowerCase((String) searchCriteria.getValue());
        Expression<String> searchExpression = cb.lower(root.get(searchCriteria.getKey()));

        return switch (searchCriteria.getOperation()) {
            case EQUAL -> cb.equal(searchExpression, searchValueLowerCase);
            case NOT_EQUAL -> cb.notEqual(searchExpression, searchValueLowerCase);
            case CONTAIN -> cb.like(searchExpression, "%" + searchValueLowerCase + "%");
            default -> null;
        };
    }
}