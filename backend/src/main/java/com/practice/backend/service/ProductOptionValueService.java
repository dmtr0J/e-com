package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.ProductOptionValueDao;
import com.practice.backend.model.entity.ProductOptionValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionValueService extends AbstractService<ProductOptionValue> {

    private final ProductOptionValueDao dao;

    @Override
    protected AbstractIdentifiableDao<ProductOptionValue> getDao() {return this.dao; }
}
