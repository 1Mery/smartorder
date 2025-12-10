package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.command.CancelOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.exception.OrderNotFoundException;
import com.turkcell.orderservice.application.mapper.OrderMapper;
import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderId;
import com.turkcell.orderservice.domain.ports.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderCommandHandler {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public CancelOrderCommandHandler(OrderRepository orderRepository,
                                     OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderResponse cancelOrder(CancelOrderCommand command) {
        OrderId orderId = new OrderId(command.orderId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.cancel();

        orderRepository.save(order);

        return orderMapper.toResponse(order);
    }
}
