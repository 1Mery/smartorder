package com.turkcell.productservice.domain.model;

import com.turkcell.productservice.domain.exception.ProductException;

public record Stock(int value) {
    //primitive değer olduğunda null olamaz zaten

    public static Stock of(int rawValue){
        if (rawValue<0){
            throw new ProductException("Stock cannot be negative");
        }
        return new Stock(rawValue);
    }

    public Stock increase(int amount) {

        if (amount <= 0) {
            throw new ProductException("Increase amount must be greater than 0");
        }

        return new Stock(this.value + amount);
    }

    public Stock decrease(int amount) {

        if (amount <= 0) {
            throw new ProductException("Decrease amount must be greater than 0");
        }

        if (this.value - amount < 0) {
            throw new ProductException("Insufficient stock");
        }

        return new Stock(this.value - amount);
    }
}
