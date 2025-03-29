package com.practice.backend.dao;

import com.practice.backend.model.entity.ProductOption;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionDao extends AbstractIdentifiableDao<ProductOption> {

    private final ProductOptionRepository productOptionRepository;

    @Override
    protected BaseIdentifiableRepository<ProductOption> getRepository() {
        return this.productOptionRepository;
    }
}
