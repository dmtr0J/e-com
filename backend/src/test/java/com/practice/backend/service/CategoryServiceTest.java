package com.practice.backend.service;

import com.practice.backend.builder.CategoryBuilder;
import com.practice.backend.model.entity.Category;

public class CategoryServiceTest {



    public void testCreateCategory() {
        Category c = CategoryBuilder.builder().build();
    }
}
