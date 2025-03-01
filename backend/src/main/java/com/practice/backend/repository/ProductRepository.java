package com.practice.backend.repository;

import com.practice.backend.model.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseIdentifiableRepository<Product> {
}
