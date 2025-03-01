package com.practice.backend.repository;

import com.practice.backend.model.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends BaseIdentifiableRepository<OrderItem> {
}
