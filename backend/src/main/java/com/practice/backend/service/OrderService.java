package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.OrderDao;
import com.practice.backend.model.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService extends AbstractService<Order> {

    private final OrderDao dao;

    @Override
    protected AbstractIdentifiableDao<Order> getDao() {
        return this.dao;
    }
}
