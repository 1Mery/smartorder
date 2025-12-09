package com.turkcell.productservice.domain.model;

import com.turkcell.productservice.domain.exception.ProductException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount) {

    public Money {
        Objects.requireNonNull(amount, "Amount cannot be null");
    }

    public static Money of(BigDecimal rawAmount) {

        if (rawAmount == null) {
            throw new ProductException("Amount cannot be null");
        }

        //Negatif değer kontrolü
        if (rawAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductException("Amount cannot be negative");
        }

        //   HALF_UP -> finans uygulamalarında en çok kullanılan yuvarlama
        BigDecimal normalized = rawAmount.setScale(2, RoundingMode.HALF_UP);

        return new Money(normalized);
    }
}
