package com.turkcell.orderservice.infrastructure.kafka.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class OutboxPayloadSerializer {

    private final ObjectMapper objectMapper; //jsona çevirecek kısım

    public OutboxPayloadSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Outbox payload serialize failed", e);
        }
    }
}
