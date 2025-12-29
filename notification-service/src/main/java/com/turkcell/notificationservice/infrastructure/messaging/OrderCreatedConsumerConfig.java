package com.turkcell.notificationservice.infrastructure.messaging;

import com.turkcell.notificationservice.application.dto.OrderCreatedEventDto;
import com.turkcell.notificationservice.application.usecase.OrderCreatedNotificationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderCreatedConsumerConfig {

    @Bean
    public Consumer<OrderCreatedEventDto> orderCreatedConsumer(OrderCreatedNotificationUseCase useCase) {
        return dto -> {
            System.out.println("[KAFKA IN] ORDER_CREATED eventId=" + dto.eventId() + " orderId=" + dto.orderId());
            useCase.sendCreate(dto);
        };
    }
}