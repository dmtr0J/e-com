package com.practice.backend.filtering.common;

import com.practice.backend.filtering.specification.SpecificationBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FilterableProperty<EntityType> {

    private String propertyName;
    private Class<?> expectedType;
    private List<FilteringOperation> operators;
    private SpecificationBuilder<EntityType> specificationBuilder;

}