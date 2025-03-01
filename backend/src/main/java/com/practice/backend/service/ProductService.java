package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.ProductDao;
import com.practice.backend.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService extends AbstractService<Product> {

    private final ProductDao dao;

    @Override
    protected AbstractIdentifiableDao<Product> getDao() {return this.dao; }
}
