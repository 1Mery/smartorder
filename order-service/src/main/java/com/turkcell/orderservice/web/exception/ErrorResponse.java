package com.turkcell.orderservice.web.exception;

public record ErrorResponse(String message, String code) {
    public static ErrorResponse of(String message, String code) {
        return new ErrorResponse(message, code);
    }
}
