package com.turkcell.notificationservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record EventId(UUID value) {
    public EventId{
        Objects.requireNonNull(value,"Cannot be null");
    }
}
