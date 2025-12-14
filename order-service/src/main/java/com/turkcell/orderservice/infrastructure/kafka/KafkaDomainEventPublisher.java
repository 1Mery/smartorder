package com.turkcell.orderservice.infrastructure.kafka;

import com.turkcell.orderservice.application.ports.DomainEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaDomainEventPublisher  implements DomainEventPublisher {

    private final StreamBridge streamBridge;

    public KafkaDomainEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publish(Object event) {

        boolean sent=streamBridge.send("orderEvents-out-0",event);
        if(!sent){
            log.error("Order event could not sent to kafka");
        }
        else {
            log.info("Order event sent to kafka"+event);
        }
    }
}
