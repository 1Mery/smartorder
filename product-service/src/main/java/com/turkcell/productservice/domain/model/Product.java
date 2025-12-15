package com.turkcell.productservice.domain.model;

import com.turkcell.productservice.domain.exception.ProductException;

import java.util.Objects;

public class Product {

    private final ProductId productId;
    private String productName;
    private Money amount;
    private Stock value;
    private boolean active;

    private Product(ProductId productId, String productName, Money amount, Stock value, boolean active) {
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
        this.value = value;
        this.active = active;
    }

    public static Product create(
            String productName,
            Money amount,
            Stock value
    ){
        validateProductName(productName);
        Objects.requireNonNull(amount,"Amount cannot be null");
        Objects.requireNonNull(value);

        return new Product(ProductId.generate(),productName,amount,value,true);
    }

    public static Product rehydrate(ProductId productId,
                                     String productName,
                                     Money amount,
                                     Stock value,
                                     boolean active){
        return new Product(productId,productName,amount,value,active);
    }

    public void rename(String newName) {
        validateProductName(newName);
        this.productName = newName.trim();
    }

    public void changePrice(Money newPrice) {
        Objects.requireNonNull(newPrice);
        this.amount = newPrice;
    }

    public void increaseStock(int amount) {
        this.value = this.value.increase(amount);
    }

    public void decreaseStock(int amount) {
        this.value = this.value.decrease(amount);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    private static void validateProductName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ProductException("Product name cannot be empty");
        }
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Money getAmount() {
        return amount;
    }

    public Stock getValue() {
        return value;
    }

    public boolean isActive() {
        return active;
    }
}
