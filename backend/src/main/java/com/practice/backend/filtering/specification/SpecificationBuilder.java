package com.practice.backend.filtering.specification;

import com.practice.backend.filtering.common.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<EntityType> {
    Specification<EntityType> buildSpecification(SearchCriteria searchCriteria);
}
