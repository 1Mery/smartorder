package com.turkcell.customerservice.domain.model;

import java.util.Objects;

public record Email(String email) {
    public Email {
        Objects.requireNonNull(email, "Email cannot be null");
    }

    public static Email of(String rawEmail) {
        if (rawEmail==null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        rawEmail=rawEmail.trim();
        if (rawEmail.isBlank()){
            throw new IllegalArgumentException("Email cannot be empty");
        }

        rawEmail=rawEmail.toLowerCase();
        if (!rawEmail.contains("@")){
            throw new IllegalArgumentException("Email must contain '@'");
        }

        return new Email(rawEmail);
    }
}
