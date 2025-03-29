package com.practice.backend.converter;

import com.practice.backend.dto.CategoryRequest;
import com.practice.backend.dto.CategoryResponse;
import com.practice.backend.model.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryConverter implements Converter<Category, CategoryRequest, CategoryResponse> {

    @Override
    public Category requestToEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .parent(categoryRequest.getParent())
                .build();
    }

    @Override
    public CategoryResponse entityToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent())
                .build();
    }

    @Override
    public List<CategoryResponse> entityToResponse(List<Category> entity) {
        return entity.stream().map(this::entityToResponse).toList();
    }
}
