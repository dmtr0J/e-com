package com.practice.backend.dao;

import com.practice.backend.model.entity.Category;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryDao extends AbstractIdentifiableDao<Category> {

    private final CategoryRepository categoryRepository;

    @Override
    protected CategoryRepository getRepository() {return this.categoryRepository; }

    public Category getOneByName(String name) { return getRepository().findOneByName(name).orElse(null); }
}
