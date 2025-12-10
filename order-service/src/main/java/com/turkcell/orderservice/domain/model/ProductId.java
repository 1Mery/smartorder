package com.turkcell.orderservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {
    public ProductId{
        Objects.requireNonNull(value,"Id cannor be null");
    }

    public static ProductId generate(){
        return new ProductId(UUID.randomUUID());
    }
}
