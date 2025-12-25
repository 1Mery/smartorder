package com.turkcell.notificationservice.domain.model;

import com.turkcell.notificationservice.domain.exception.NotificationException;

import java.util.Objects;

public record Recipient(String email) {
    public Recipient{
        Objects.requireNonNull(email,"Email cannot be null");

        if (!email.contains("@")){
            throw new NotificationException("Email should contain '@' ");
        }
        if (email.isBlank()){
            throw new NotificationException("Email cannot be blank");
        }
    }
}
