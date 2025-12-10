package com.turkcell.orderservice.domain.ports;

import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId orderId);
    void delete(OrderId orderId);
}
