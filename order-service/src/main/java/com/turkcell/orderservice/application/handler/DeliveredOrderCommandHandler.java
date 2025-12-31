package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.command.DeliveredOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.event.OrderDeliveredEvent;
import com.turkcell.orderservice.application.event.OrderItemEvent;
import com.turkcell.orderservice.application.exception.OrderNotFoundException;
import com.turkcell.orderservice.application.mapper.OrderMapper;
import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderId;
import com.turkcell.orderservice.domain.model.OrderItem;
import com.turkcell.orderservice.domain.ports.OrderRepository;
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxEventEntity;
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxEventRepository;
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxPayloadSerializer;
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeliveredOrderCommandHandler {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OutboxEventRepository eventRepository;
    private final OutboxPayloadSerializer serializer;

    public DeliveredOrderCommandHandler(OrderRepository orderRepository, OrderMapper orderMapper, OutboxEventRepository eventRepository, OutboxPayloadSerializer serializer) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.eventRepository = eventRepository;
        this.serializer = serializer;
    }

    public OrderResponse deliveredOrder(DeliveredOrderCommand command) {
        OrderId orderId = new OrderId(command.orderId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.delivered();

        orderRepository.save(order);

        List<OrderItemEvent> itemEvents=new ArrayList<>();

        for (OrderItem item: order.getItems()){
            OrderItemEvent itemEvent=new OrderItemEvent(
                    item.getProductId().value(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getLineTotal()
            );
           itemEvents.add(itemEvent);
        }

        OrderDeliveredEvent event=new OrderDeliveredEvent(
                order.getOrderId().value(),
                order.getCustomerId().value(),
                order.getTotalPrice(),
                order.getStatus(),
                itemEvents
        );

        String payload =serializer.toJson (event);

        OutboxEventEntity eventEntity=new OutboxEventEntity();
        eventEntity.setEventId(UUID.randomUUID());
        eventEntity.setOrderId(order.getOrderId().value());
        eventEntity.setEventType("OrderDeliveredEvent");
        eventEntity.setPayload(payload);
        eventEntity.setStatus(OutboxStatus.PENDING);
        eventEntity.setCreatedAt(Instant.now());
        eventEntity.setBindingName("orderDeliveredEvents-out-0");

        eventRepository.save(eventEntity);

        return orderMapper.toResponse(order);
    }
}
