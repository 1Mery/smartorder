package com.turkcell.product_service.application.service;

import com.turkcell.product_service.application.dto.request.CreateProductRequest;
import com.turkcell.product_service.domain.entity.Product;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import com.turkcell.product_service.infrastructure.persistence.mapper.ProductEntityMapper;

import jakarta.ws.rs.client.Entity;

public class CreateProductUseCase {

    private final ProductEntityMapper mapper;
    private final ProductRepository repository;

    public CreateProductUseCase(ProductEntityMapper mapper, ProductRepository repository) {
        this.mapper = mapper;
        this.repository=repository;
    }

    public Product createProduct (CreateProductRequest request){
        Product entity=mapper.toDomain(request);
        repository.save(entity);

        return mapper.toResponse(entity);

    }

}
