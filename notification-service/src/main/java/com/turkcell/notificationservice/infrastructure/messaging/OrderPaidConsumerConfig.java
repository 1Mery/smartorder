package com.turkcell.notificationservice.infrastructure.messaging;

import com.turkcell.notificationservice.application.dto.OrderPaidEventDto;
import com.turkcell.notificationservice.application.usecase.OrderPaidNotificationUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderPaidConsumerConfig {

    @Bean
    public Consumer<OrderPaidEventDto> orderPaidEventDtoConsumer(OrderPaidNotificationUseCase useCase){
        return payload ->{
            log.info("[KAFKA IN] order-paid-events payload = " + payload);
        };
    }
}
