package com.turkcell.orderservice.application.ports;

import com.turkcell.orderservice.application.event.OrderCancelledEvent;
import com.turkcell.orderservice.application.event.OrderCreatedEvent;
import com.turkcell.orderservice.application.event.OrderDeliveredEvent;
import com.turkcell.orderservice.application.event.OrderPaidEvent;

public interface OrderEventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);

    void publishOrderPaid(OrderPaidEvent event);

    void publishOrderDelivered(OrderDeliveredEvent event);

    void publishOrderCancelled(OrderCancelledEvent event);
}
