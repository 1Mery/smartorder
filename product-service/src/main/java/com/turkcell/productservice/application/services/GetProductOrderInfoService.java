package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.dto.ProductOrderInfoResponse;
import com.turkcell.productservice.application.dto.ProductResponse;
import com.turkcell.productservice.application.exception.ProductNotFoundException;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProductOrderInfoService {

    private final ProductRepository repository;

    public GetProductOrderInfoService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductOrderInfoResponse get(ProductId id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return new ProductOrderInfoResponse(
                product.getAmount().amount(),
                product.getValue().value()
        );
    }
}