package com.practice.backend.api.v1.controller;

import com.practice.backend.api.v1.ApiConstants;
import com.practice.backend.api.v1.common.AbstractController;
import com.practice.backend.converter.CategoryConverter;
import com.practice.backend.converter.Converter;
import com.practice.backend.dto.CategoryRequest;
import com.practice.backend.dto.CategoryResponse;
import com.practice.backend.filtering.CategorySpecificationBuilder;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.model.entity.Category;
import com.practice.backend.model.entity.User;
import com.practice.backend.service.AbstractService;
import com.practice.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.CATEGORY_PATH)
public class CategoryController extends AbstractController<Category, CategoryRequest, CategoryResponse> {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;
    private final CategorySpecificationBuilder categorySpecificationBuilder;

    @Override
    public Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    public AbstractService<Category> getService() {
        return this.categoryService;
    }

    @Override
    public Converter<Category, CategoryRequest, CategoryResponse> getConverter() {
        return this.categoryConverter;
    }

    @Override
    public CategoryResponse convertEntityToResponse(Category entity, List<String> entitiesToExpand) {
        return getConverter().entityToResponse(entity);
    }

    @Override
    protected Category updateEntity(CategoryRequest request, Category entity) {
        return null;
    }

    @Override
    public EntityFilterSpecificationBuilder<Category> getFilterSpecificationsBuilder() {
        return this.categorySpecificationBuilder;
    }

    @GetMapping("/roots")
    public List<CategoryResponse> getRootCategories() {
        return getConverter().entityToResponse(
                this.categoryService.getRootCategories());
    }

    @GetMapping("/sub/{id}")
    public List<CategoryResponse> getSubcategories(@PathVariable Long id) {
        return getConverter().entityToResponse(
                this.categoryService.getSubcategories(id));
    }
}
