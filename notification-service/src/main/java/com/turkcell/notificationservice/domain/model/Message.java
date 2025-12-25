package com.turkcell.notificationservice.domain.model;

import com.turkcell.notificationservice.domain.exception.NotificationException;

import java.util.Objects;

public record Message (String value){
    public Message{
        Objects.requireNonNull(value,"Cannot be null");

        if (value.isBlank()){
            throw new NotificationException("Cannot be blank");
        }
    }
}
