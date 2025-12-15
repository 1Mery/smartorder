package com.turkcell.orderservice.infrastructure.kafka;

import com.turkcell.orderservice.application.command.ApproveOrderCommand;
import com.turkcell.orderservice.application.command.CancelOrderCommand;
import com.turkcell.orderservice.application.events.StockDecreaseFailedEvent;
import com.turkcell.orderservice.application.events.StockDecreasedEvent;
import com.turkcell.orderservice.application.handler.ApproveOrderCommandHandler;
import com.turkcell.orderservice.application.handler.CancelOrderCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class StockEventsConsumerConfig {

    private final ApproveOrderCommandHandler approveOrderCommandHandler;
    private final CancelOrderCommandHandler cancelOrderCommandHandler;

    public StockEventsConsumerConfig(ApproveOrderCommandHandler approveOrderCommandHandler,
                                     CancelOrderCommandHandler cancelOrderCommandHandler) {
        this.approveOrderCommandHandler = approveOrderCommandHandler;
        this.cancelOrderCommandHandler = cancelOrderCommandHandler;
    }

    // Stok başarılı düştüğünde gelen event
    @Bean
    public Consumer<StockDecreasedEvent> stockDecreasedEvents() {
        return event -> {
            log.info("StockDecreasedEvent received in order-service: {}", event);

            //  siparişi approve et
            ApproveOrderCommand command = new ApproveOrderCommand(event.orderId());
            approveOrderCommandHandler.approveOrder(command);

            log.info("Order approved after stock decrease. orderId={}", event.orderId());
        };
    }

    // Stok yetersiz olduğunda gelen event
    @Bean
    public Consumer<StockDecreaseFailedEvent> stockDecreaseFailedEvents() {
        return event -> {
            log.info("StockDecreaseFailedEvent received in order-service: {}", event);

            //siparişi cancel et
            CancelOrderCommand command = new CancelOrderCommand(event.orderId());
            cancelOrderCommandHandler.cancelOrder(command);

            log.info(
                    "Order cancelled after stock decrease failed. orderId={}, requested={}, available={}",
                    event.orderId(),
                    event.requestQuantity(),
                    event.availableQuantity()
            );
        };
    }
}
