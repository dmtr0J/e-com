package com.practice.backend.dao;

import com.practice.backend.model.entity.OrderItem;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemDao extends AbstractIdentifiableDao<OrderItem> {

    private final OrderItemRepository orderItemRepository;

    @Override
    protected BaseIdentifiableRepository<OrderItem> getRepository() {return this.orderItemRepository; }
}
