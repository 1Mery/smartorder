package com.turkcell.orderservice.application.mapper;

import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.domain.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getOrderId().value(),
                order.getCustomerId().value(),
                order.getProductId().value(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getTotalPrice(),
                order.getStatus().toString()
        );
    }
}

