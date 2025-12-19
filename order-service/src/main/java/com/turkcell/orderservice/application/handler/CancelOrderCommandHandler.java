package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.command.CancelOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.event.OrderCancelledEvent;
import com.turkcell.orderservice.application.event.OrderItemEvent;
import com.turkcell.orderservice.application.exception.OrderNotFoundException;
import com.turkcell.orderservice.application.mapper.OrderMapper;
import com.turkcell.orderservice.application.ports.OrderEventPublisher;
import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderId;
import com.turkcell.orderservice.domain.model.OrderItem;
import com.turkcell.orderservice.domain.ports.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CancelOrderCommandHandler {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderEventPublisher publisher;

    public CancelOrderCommandHandler(OrderRepository orderRepository, OrderMapper orderMapper, OrderEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.publisher = publisher;
    }

    public OrderResponse cancelOrder(CancelOrderCommand command) {
        OrderId orderId = new OrderId(command.orderId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.cancel();

        orderRepository.save(order);

        List<OrderItemEvent> itemEvents= new ArrayList<>();

        for (OrderItem item: order.getItems()){
            OrderItemEvent itemEvent=new OrderItemEvent(
                    item.getProductId().value(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getLineTotal()
            );
            itemEvents.add(itemEvent);
        }

        OrderCancelledEvent event=new OrderCancelledEvent(
          order.getOrderId().value(),
          order.getCustomerId().value(),
          order.getTotalPrice(),
          order.getStatus(),
          itemEvents
        );

        publisher.publishOrderCancelled(event);

        return orderMapper.toResponse(order);
    }
}
