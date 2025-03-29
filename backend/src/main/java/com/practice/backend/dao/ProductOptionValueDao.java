package com.practice.backend.dao;

import com.practice.backend.model.entity.ProductOptionValue;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.ProductOptionValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionValueDao extends AbstractIdentifiableDao<ProductOptionValue> {

    private final ProductOptionValueRepository productOptionValueRepository;

    @Override
    protected BaseIdentifiableRepository<ProductOptionValue> getRepository() {
        return this.productOptionValueRepository;
    }
}
