package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.CategoryDao;
import com.practice.backend.dao.exception.EntityNotFoundException;
import com.practice.backend.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService extends AbstractService<Category> {

    private final CategoryDao dao;

    @Override
    protected CategoryDao getDao() {return this.dao; }

    public Category getOneByName(String name) {
        Category entity = getDao().getOneByName(name);

        if (entity == null) {
            throw new EntityNotFoundException(String.format("Category with name %s was not found", name));

        }

        return entity;
    }
}
