package com.turkcell.orderservice.application.mapper;

import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {

        //order içindeki domain item listesini al
        List<OrderResponse.OrderItemResponse> itemResponses = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            OrderResponse.OrderItemResponse itemResponse = toItemResponse(item);
            itemResponses.add(itemResponse);
        }

        return new OrderResponse(
                order.getOrderId().value(),
                order.getCustomerId().value(),
                order.getTotalPrice(),
                order.getStatus().toString(),
                itemResponses
        );
    }

    private OrderResponse.OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderResponse.OrderItemResponse(
                item.getProductId().value(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getLineTotal()
        );
    }
}

