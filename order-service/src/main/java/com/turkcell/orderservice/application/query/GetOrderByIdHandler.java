package com.turkcell.orderservice.application.query;

import com.turkcell.orderservice.application.command.GetOrderByIdCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.exception.OrderNotFoundException;
import com.turkcell.orderservice.application.mapper.OrderMapper;
import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderId;
import com.turkcell.orderservice.domain.ports.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class GetOrderByIdHandler {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public GetOrderByIdHandler(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public OrderResponse getOrder(GetOrderByIdCommand command){
        OrderId orderId=new OrderId(command.orderId());
        Order order=repository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Order not found"));

        return mapper.toResponse(order);
    }
}
