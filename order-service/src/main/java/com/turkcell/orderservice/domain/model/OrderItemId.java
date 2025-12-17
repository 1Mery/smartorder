package com.turkcell.orderservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record OrderItemId(UUID value) {
    public OrderItemId{
        Objects.requireNonNull(value,"Id cannot be null");
    }

    public static OrderItemId generate(){
        return new OrderItemId(UUID.randomUUID());
    }
}
