package com.turkcell.orderservice.domain.model;

public enum OrderStatus {
    CREATED,  //pending payment
    PAID,  // paid/ confirmed
    DELIVERED,  //delivered
    CANCELLED
}
