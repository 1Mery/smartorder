package com.turkcell.notificationservice.infrastructure.messaging;

import java.util.function.Consumer;

import com.turkcell.notificationservice.application.dto.OrderCancelledEventDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderCancelledConsumerConfig {

    @Bean
    public Consumer<OrderCancelledEventDto> orderCancelledConsumer() {
        return payload -> {
            System.out.println("[KAFKA IN] order-cancelled-events payload = " + payload);
        };
    }
}

