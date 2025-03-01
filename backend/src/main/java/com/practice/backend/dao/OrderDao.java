package com.practice.backend.dao;

import com.practice.backend.model.entity.Order;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDao extends AbstractIdentifiableDao<Order> {

    private final OrderRepository orderRepository;

    @Override
    protected BaseIdentifiableRepository<Order> getRepository() {
        return this.orderRepository;
    }

}
