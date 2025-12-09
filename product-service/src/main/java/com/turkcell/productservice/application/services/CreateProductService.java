package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.dto.CreateProductRequest;
import com.turkcell.productservice.application.dto.ProductResponse;
import com.turkcell.productservice.application.mapper.ProductMapper;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.ports.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    public CreateProductService(ProductMapper mapper, ProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ProductResponse create(CreateProductRequest request){
        Product product=mapper.toDomain(request);
        repository.save(product);

        return mapper.toResponse(product);
    }
}
