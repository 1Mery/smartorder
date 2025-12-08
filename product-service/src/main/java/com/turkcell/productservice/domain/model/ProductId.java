package com.turkcell.productservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {

    public ProductId{
        Objects.requireNonNull(value,"Id cannot be null");
    }

    public static ProductId generate(){
        return new ProductId(UUID.randomUUID());
    }
}
