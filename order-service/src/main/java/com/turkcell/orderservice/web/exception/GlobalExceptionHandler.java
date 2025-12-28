package com.turkcell.orderservice.web.exception;

import com.turkcell.orderservice.application.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 409
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficient(InsufficientStockException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(ex.getMessage(), "INSUFFICIENT_STOCK"));
    }

    // 404
    @ExceptionHandler({CustomerNotFoundException.class, ProductNotFoundException.class, OrderNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        String code = (ex instanceof CustomerNotFoundException) ? "CUSTOMER_NOT_FOUND"
                : (ex instanceof ProductNotFoundException) ? "PRODUCT_NOT_FOUND"
                : "ORDER_NOT_FOUND";

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(ex.getMessage(), code));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ex.getMessage(), "BAD_REQUEST"));
    }

}
