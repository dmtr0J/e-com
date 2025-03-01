package com.practice.backend.builder;

import com.practice.backend.dao.OrderDao;
import com.practice.backend.model.entity.Order;
import com.practice.backend.model.enums.OrderStatus;
import com.practice.backend.model.enums.PaymentMethod;
import com.practice.backend.repository.OrderRepository;
import com.practice.backend.service.OrderService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OrderBuilder {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDao orderDAO;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private long randomLong;

    public OrderBuilder() {
        initDefaultData();
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public Order build() {
        order = new Order();
        initDefaultData();
        return order;
    }

    private void initDefaultData() {
        order = new Order();

        randomLong = ThreadLocalRandom.current().nextLong(1, 999999);

        order.setId(randomLong);
        // should be used user builder
        // like UserBuilder.builder().build();
        //order.setUser();
        // address builder
        //order.setAddress();
        // order items builder
        order.setOrderItems(List.of(

        ));
        order.setPaymentMethod(PaymentMethod.CARD);
        order.setTotalPrice(BigDecimal.valueOf(randomLong));
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDueDate(Instant.now());
        order.setCreatedAt(Instant.now());
    }

    // also should add setter for fields
    // in that manner
    public OrderBuilder setId() {
        order.setId(randomLong);
        return this;
    }


}
