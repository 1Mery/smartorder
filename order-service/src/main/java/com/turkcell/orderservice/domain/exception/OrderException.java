package com.turkcell.orderservice.domain.exception;

public class OrderException extends RuntimeException{
    public OrderException(String message) {
        super(message);
    }
}
