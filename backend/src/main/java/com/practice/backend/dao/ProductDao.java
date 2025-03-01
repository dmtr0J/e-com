package com.practice.backend.dao;

import com.practice.backend.model.entity.Product;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDao extends AbstractIdentifiableDao<Product> {

    private final ProductRepository productRepository;

    @Override
    protected BaseIdentifiableRepository<Product> getRepository() {return this.productRepository;}
}
