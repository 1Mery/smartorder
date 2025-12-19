package com.turkcell.orderservice.infrastructure.kafka;

import com.turkcell.orderservice.application.event.OrderCancelledEvent;
import com.turkcell.orderservice.application.event.OrderCreatedEvent;
import com.turkcell.orderservice.application.event.OrderDeliveredEvent;
import com.turkcell.orderservice.application.event.OrderPaidEvent;
import com.turkcell.orderservice.application.ports.OrderEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final StreamBridge streamBridge;

    public KafkaOrderEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        boolean sent=streamBridge.send("orderCreateEvents-out-0",event);
        if(!sent){
            log.error("Order event could not sent to kafka");
        }
        else {
            log.info("Order event sent to kafka"+event);
        }
    }

    @Override
    public void publishOrderPaid(OrderPaidEvent event) {
        boolean sent=streamBridge.send("orderPaidEvents-out-0",event);
        if(!sent){
            log.error("Order event could not sent to kafka");
        }
        else {
            log.info("Order event sent to kafka"+event);
        }
    }

    @Override
    public void publishOrderDelivered(OrderDeliveredEvent event) {
        boolean sent=streamBridge.send("orderDeliveredEvents-out-0",event);
        if(!sent){
            log.error("Order event could not sent to kafka");
        }
        else {
            log.info("Order event sent to kafka"+event);
        }
    }

    @Override
    public void publishOrderCancelled(OrderCancelledEvent event) {
        boolean sent=streamBridge.send("orderCancelEvents-out-0",event);
        if(!sent){
            log.error("Order event could not sent to kafka");
        }
        else {
            log.info("Order event sent to kafka"+event);
        }
    }
}
