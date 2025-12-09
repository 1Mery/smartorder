package com.turkcell.productservice.infrastructure.persistence;

import com.turkcell.productservice.domain.model.Money;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {

    public JpaProductEntity toEntity(Product product){
        JpaProductEntity productEntity=new JpaProductEntity();
        productEntity.setProductId(product.getProductId().value());
        productEntity.setProductName(product.getProductName());
        productEntity.setAmount(product.getAmount().amount());
        productEntity.setStock(product.getValue().value());
        productEntity.setActive(product.isActive());

        return productEntity;
    }

    public Product toDomain(JpaProductEntity entity){
        return Product.rehydrate(
                new ProductId(entity.getProductId()),
                entity.getProductName(),
                Money.of(entity.getAmount()),
                Stock.of(entity.getStock()),
                entity.isActive()
        );
    }
}
