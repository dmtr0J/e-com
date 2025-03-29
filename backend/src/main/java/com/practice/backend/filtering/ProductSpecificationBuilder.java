package com.practice.backend.filtering;

import com.practice.backend.filtering.common.FilterableProperty;
import com.practice.backend.filtering.specification.BigDecimalComparisonSpecificationBuilder;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.filtering.specification.EqualingOrNullSpecificationBuilder;
import com.practice.backend.filtering.specification.StringComparisonSpecificationBuilder;
import com.practice.backend.model.entity.Product;
import com.practice.backend.model.entity.Product_;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSpecificationBuilder implements EntityFilterSpecificationBuilder<Product> {

    private static final List<FilterableProperty<Product>> FILTERABLE_PROPERTIES = List.of(
            new FilterableProperty<>(Product_.NAME, String.class,
                    EqualingOrNullSpecificationBuilder.SUPPORTED_OPERATORS,
                    new EqualingOrNullSpecificationBuilder<>()),

            new FilterableProperty<>(Product_.DESCRIPTION, String.class,
                    StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new StringComparisonSpecificationBuilder<>()),

            new FilterableProperty<>(Product_.PRICE, Object.class,
                    BigDecimalComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new BigDecimalComparisonSpecificationBuilder<>()));

    @Override
    public List<FilterableProperty<Product>> getFilterableProperties() {
        return FILTERABLE_PROPERTIES;
    }
}
