package com.practice.backend.filtering;

import com.practice.backend.filtering.common.FilterableProperty;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.filtering.specification.EqualingSpecificationBuilder;
import com.practice.backend.filtering.specification.LocalDateComparisonSpecificationBuilder;
import com.practice.backend.filtering.specification.StringComparisonSpecificationBuilder;
import com.practice.backend.model.entity.User;
import com.practice.backend.model.entity.User_;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserSpecificationBuilder implements EntityFilterSpecificationBuilder<User> {

    private static final List<FilterableProperty<User>> FILTERABLE_PROPERTIES = List.of(
            new FilterableProperty<>(User_.EMAIL, String.class,
                    StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new StringComparisonSpecificationBuilder<>()),

            new FilterableProperty<>(User_.FIRST_NAME, String.class,
                    StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new StringComparisonSpecificationBuilder<>()),

            new FilterableProperty<>(User_.MIDDLE_NAME, String.class,
                    StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new StringComparisonSpecificationBuilder<>()),

            new FilterableProperty<>(User_.LAST_NAME, String.class,
                    StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new StringComparisonSpecificationBuilder<>()),

            new FilterableProperty<>(User_.PHONE, String.class,
                    EqualingSpecificationBuilder.SUPPORTED_OPERATORS,
                    new EqualingSpecificationBuilder<>()),

            new FilterableProperty<>(User_.BIRTH_DATE, LocalDate.class,
                    LocalDateComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                    new LocalDateComparisonSpecificationBuilder<>())
    );

    @Override
    public List<FilterableProperty<User>> getFilterableProperties() {
        return FILTERABLE_PROPERTIES;
    }
}
