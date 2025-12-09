package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.dto.DecreaseStockRequest;
import com.turkcell.productservice.application.dto.ProductResponse;
import com.turkcell.productservice.application.exception.ProductNotFoundException;
import com.turkcell.productservice.application.mapper.ProductMapper;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DecreaseStockService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    public DecreaseStockService(ProductMapper mapper, ProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ProductResponse decrease(ProductId productId, DecreaseStockRequest request){
        Product product=repository.findById(productId).
                orElseThrow(()-> new ProductNotFoundException("Product not found"));

        int stock=request.stockAmount();
        product.decreaseStock(stock);
        repository.save(product);

        return mapper.toResponse(product);
    }
}
