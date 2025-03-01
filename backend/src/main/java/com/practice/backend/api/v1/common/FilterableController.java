package com.practice.backend.api.v1.common;

import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;

public interface FilterableController<T> {

    default EntityFilterSpecificationBuilder<T> getFilterSpecificationsBuilder() {
        return null;
    }
}
