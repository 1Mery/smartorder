package com.turkcell.orderservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID value) {
    public OrderId{
        Objects.requireNonNull(value,"Id cannor be null");
    }

    public static OrderId generate(){
        return new OrderId(UUID.randomUUID());
    }
}
