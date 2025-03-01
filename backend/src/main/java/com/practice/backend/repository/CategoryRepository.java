package com.practice.backend.repository;

import com.practice.backend.model.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseIdentifiableRepository<Category> {

    Optional<Category> findOneByName(String name);
}
