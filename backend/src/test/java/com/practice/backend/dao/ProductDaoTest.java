package com.practice.backend.dao;

import com.practice.backend.model.entity.Product;
import com.practice.backend.repository.ProductRepository;
import org.mockito.Mock;

public class ProductDaoTest extends AbstractIdentifiableDaoTest<Product, ProductDao, ProductRepository>{
    @Mock
    ProductRepository productRepository;

    @Override
    public Product createEntity() {
        return Product.builder().build();
    }

    @Override
    public ProductDao createDao() {
        return new ProductDao(productRepository);
    }

    @Override
    public ProductRepository createRepository() {
        return productRepository;
    }
}
