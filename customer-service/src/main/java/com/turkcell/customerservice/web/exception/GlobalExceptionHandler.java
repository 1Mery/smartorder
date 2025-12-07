package com.turkcell.customerservice.web.exception;

import com.turkcell.customerservice.application.exception.CustomerNotFoundException;
import com.turkcell.customerservice.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Tüm controller'lardaki hataları merkezi olarak yakalar
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> handleDomainException(DomainException ex) {
        // DomainException -> İş kuralı ihlali
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(ex.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException ex) {
        // GetCustomerById içinde fırlatılır
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        // Sistemde yakalanmayan beklenmeyen hatalar
        // Kullanıcıya sistemin çöktüğünü belli etmez, düzgün bir error döner
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body("Internal server error: " + ex.getMessage());
    }
}
