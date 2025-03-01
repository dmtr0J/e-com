package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.ProductOptionDao;
import com.practice.backend.model.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionService extends AbstractService<ProductOption> {

    private final ProductOptionDao dao;

    @Override
    protected AbstractIdentifiableDao<ProductOption> getDao() {return this.dao; }
}
