package com.turkcell.notificationservice.infrastructure.messaging;

import java.util.function.Consumer;
import com.turkcell.notificationservice.application.dto.OrderDeliveredEventDto;
import com.turkcell.notificationservice.application.usecase.OrderDeliveredNotificationUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OrderDeliveredConsumerConfig {

    @Bean
    public Consumer<OrderDeliveredEventDto> orderDeliveredConsumer(OrderDeliveredNotificationUseCase useCase) {
        return payload -> {
            log.info("[KAFKA IN] order-delivered-events payload = " + payload);
        };
    }
}