package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.dto.ProductResponse;
import com.turkcell.productservice.application.dto.RenameProductRequest;
import com.turkcell.productservice.application.exception.ProductNotFoundException;
import com.turkcell.productservice.application.mapper.ProductMapper;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;

public class RenameProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    public RenameProductService(ProductMapper mapper, ProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ProductResponse renameName(ProductId productId,RenameProductRequest request){
        Product product=repository.findById(productId).
                orElseThrow(()-> new ProductNotFoundException("Product not found"));

        String newName=request.newName();
        product.rename(newName);

        repository.save(product);
        return mapper.toResponse(product);
    }
}
