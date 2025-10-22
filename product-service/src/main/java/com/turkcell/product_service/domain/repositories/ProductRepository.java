package com.turkcell.product_service.domain.repositories;

import com.turkcell.product_service.domain.entity.Product;
import com.turkcell.product_service.domain.entity.Product.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);
    Optional<Product> findById(ProductId productId);
    List<Product> findAll();
    List<Product> findAllPaged(Integer pageIndex, Integer pageSize);
    void delete(ProductId productId);
}
