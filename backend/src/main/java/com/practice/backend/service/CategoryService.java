package com.practice.backend.service;

import com.practice.backend.dao.CategoryDao;
import com.practice.backend.dao.exception.EntityNotFoundException;
import com.practice.backend.dao.specification.EqualSpecification;
import com.practice.backend.dao.specification.NullSpecification;
import com.practice.backend.model.entity.Category;
import com.practice.backend.model.entity.Category_;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService extends AbstractService<Category> {

    private final CategoryDao dao;

    @Override
    protected CategoryDao getDao() {
        return this.dao;
    }

    public List<Category> getRootCategories() {
        Specification<Category> specification = new NullSpecification<>(Category_.PARENT);
        return getDao().getAll(specification);
    }

    public List<Category> getSubcategories(Long id) {
        Category parentCategory = getDao().getOneById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found with id " + id));
        Specification<Category> specification = new EqualSpecification<>(Category_.PARENT, parentCategory);
        return getDao().getAll(specification);
    }

    public Category getOneByName(String name) {
        Specification<Category> specification = new EqualSpecification<>(Category_.NAME, name);
        return getDao().getOne(specification);
    }
}
