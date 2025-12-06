package com.turkcell.customerservice.domain.model;

import com.turkcell.customerservice.domain.exception.DomainException;

import java.util.Objects;

public record Phone(String value) {

    public Phone {
        Objects.requireNonNull(value, "Phone cannot be null");
    }

    public static Phone of(String rawPhone) {

        if (rawPhone == null) {
            throw new DomainException("Phone cannot be null");
        }

        //Trim aynı anlama gelen farklı yazımları tek standart yapar
        rawPhone = rawPhone.trim();

        if (rawPhone.isBlank()) {
            throw new DomainException("Phone cannot be blank");
        }

        //Sadece rakamlardan oluşmalı
        for (int i = 0; i < rawPhone.length(); i++) {
            char ch = rawPhone.charAt(i);
            if (!Character.isDigit(ch)) {
                throw new DomainException("Phone must contain only digits");
            }
        }

        //Minimum uzunluk kontrolü
        if (rawPhone.length() !=11) {
            throw new DomainException("Phone must be 11 digits");
        }

        return new Phone(rawPhone);
    }
}
