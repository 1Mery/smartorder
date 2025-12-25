package com.turkcell.notificationservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record NotificationId(UUID value) {
    public NotificationId{
        Objects.requireNonNull(value,"Cannot be null");
    }

    public static NotificationId generate(){
        return new NotificationId(UUID.randomUUID());
    }
}
