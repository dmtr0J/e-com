package com.practice.backend.filtering;

import com.practice.backend.filtering.common.FilterableProperty;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.filtering.specification.StringComparisonSpecificationBuilder;
import com.practice.backend.model.entity.Category;
import com.practice.backend.model.entity.Category_;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorySpecificationBuilder implements EntityFilterSpecificationBuilder<Category> {

    private static final List<FilterableProperty<Category>> FILTERABLE_PROPERTIES = List.of(
            new FilterableProperty<>(Category_.NAME, String.class,
                    StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new StringComparisonSpecificationBuilder<>())
    );

    @Override
    public List<FilterableProperty<Category>> getFilterableProperties() {
        return FILTERABLE_PROPERTIES;
    }
}
