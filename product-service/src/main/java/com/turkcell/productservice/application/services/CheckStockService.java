package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.exception.InsufficientStockException;
import com.turkcell.productservice.application.exception.ProductNotFoundException;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckStockService {

    private final ProductRepository repository;

    public CheckStockService(ProductRepository repository) {
        this.repository = repository;
    }

    public void checkStock(ProductId productId, int quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getValue().value() < quantity) {
            throw new InsufficientStockException("Not enough stock for product " + productId);
        }
    }
}
