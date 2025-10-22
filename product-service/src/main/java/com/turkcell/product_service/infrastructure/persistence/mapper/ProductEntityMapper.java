package com.turkcell.product_service.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.turkcell.product_service.domain.entity.Product;
import com.turkcell.product_service.domain.valueobjects.Currency;
import com.turkcell.product_service.domain.valueobjects.Price;
import com.turkcell.product_service.domain.valueobjects.Stock;
import com.turkcell.product_service.infrastructure.persistence.entity.JpaProductEntity;

@Component
public class ProductEntityMapper {

    public JpaProductEntity toEntity(Product product){
        JpaProductEntity productEntity= new JpaProductEntity();
        productEntity.setId(product.getId().getValue());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setAmount(product.getPrice().getAmount());
        productEntity.setCode(product.getCurrency().getCode());
        productEntity.setCurrencyName(product.getCurrency().getName());
        productEntity.setSymbol(product.getCurrency().getSymbol());
        productEntity.setQuantity(product.getStock().getQuantity());
        return productEntity;
    }

    public Product toDomain (JpaProductEntity entity){
        return Product.reconstruct(
         Product.ProductId.fromUUID(entity.id()),
         entity.name(),
         entity.description(),
         new Price(entity.amount(), new Currency(entity.code(), entity.currencyName(), entity.symbol())),
         new Stock(entity.quantity()),
         new Currency(entity.code(), entity.currencyName(), entity.symbol()));   

    }

}