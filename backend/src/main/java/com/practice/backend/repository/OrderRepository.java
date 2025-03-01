package com.practice.backend.repository;

import com.practice.backend.model.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseIdentifiableRepository<Order> {
}
