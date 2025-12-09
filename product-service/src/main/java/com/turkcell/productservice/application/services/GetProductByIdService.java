package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.dto.ProductResponse;
import com.turkcell.productservice.application.exception.ProductNotFoundException;
import com.turkcell.productservice.application.mapper.ProductMapper;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;

public class GetProductByIdService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    public GetProductByIdService(ProductMapper mapper, ProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ProductResponse get(ProductId id) {
        Product product = repository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
        return mapper.toResponse(product);
    }
}
