package com.practice.backend.builder;

import com.practice.backend.dao.CategoryDao;
import com.practice.backend.model.entity.Category;
import com.practice.backend.repository.CategoryRepository;
import com.practice.backend.service.CategoryService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.concurrent.ThreadLocalRandom;

public class CategoryBuilder {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryDao categoryDAO;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private Long randomLong;

    public Category build() {
        Category category = this.category;
        InitDefaultValues();
        return category;
    }

    private void InitDefaultValues(){
        randomLong = ThreadLocalRandom.current().nextLong(1, 999999);
        category = new Category();
        setId(randomLong);
        setName("someName" + randomLong);

    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public CategoryBuilder setId(Long id) {
        category.setId(id);
        return this;
    }

    public CategoryBuilder setName(String name) {
        category.setName(name);
        return this;
    }

}
