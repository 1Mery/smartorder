package com.turkcell.product_service.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class JpaProductEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name="name", nullable = false, length = 255)
    private String name;

    @Column(name="description", nullable = false, length = 255)
    private String description;

    @Column(name="amount",nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;


    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Column(name = "code",nullable = false)
    private  String code;

    private  String currencyName;

    private  String symbol;

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal amount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer quantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String currencyName(){
        return currencyName;
    }

    public void setCurrencyName(String currencyName){
        this.currencyName=currencyName;
    }

    public String code(){
        return code;
    }

    public void setCode(String code){
        this.code=code;
    }

    public String symbol(){
        return symbol;
    }

    public void setSymbol(String symbol){
        this.symbol=symbol;
    }
}