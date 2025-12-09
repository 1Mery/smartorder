package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.dto.IncreaseStockRequest;
import com.turkcell.productservice.application.dto.ProductResponse;
import com.turkcell.productservice.application.exception.ProductNotFoundException;
import com.turkcell.productservice.application.mapper.ProductMapper;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class IncreaseStockService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public IncreaseStockService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductResponse increase(ProductId productId, IncreaseStockRequest request){
        Product product=repository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product not found"));

        int stock=request.stockAmount();
        product.increaseStock(stock);
        repository.save(product);

        return mapper.toResponse(product);
    }
}
