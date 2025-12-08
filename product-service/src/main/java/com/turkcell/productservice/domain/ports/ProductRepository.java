package com.turkcell.productservice.domain.ports;

import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId productId);
    void delete(ProductId id);

}
