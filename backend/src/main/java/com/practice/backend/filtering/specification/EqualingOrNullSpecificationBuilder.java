package com.practice.backend.filtering.specification;

import com.practice.backend.filtering.common.FilteringOperation;
import com.practice.backend.filtering.common.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class EqualingOrNullSpecificationBuilder<EntityType> implements SpecificationBuilder<EntityType> {

    public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
            FilteringOperation.EQUAL,
            FilteringOperation.NOT_EQUAL);

    @Override
    public Specification<EntityType> buildSpecification(SearchCriteria searchCriteria) {
        return new EqualingOrNullSpecification<>(searchCriteria);
    }

}