package com.turkcell.customerservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {
    public CustomerId{
        Objects.requireNonNull(value,"ID cannot be null");
    }

    public static CustomerId generate(){
        return new CustomerId(UUID.randomUUID());
    }
}
