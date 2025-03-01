package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.OrderItemDao;
import com.practice.backend.model.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService extends AbstractService<OrderItem> {

    private final OrderItemDao dao;

    @Override
    protected AbstractIdentifiableDao<OrderItem> getDao() {return this.dao;}
}
