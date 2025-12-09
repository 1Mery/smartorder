package com.turkcell.productservice.application.services;

import com.turkcell.productservice.application.dto.ChangePriceRequest;
import com.turkcell.productservice.application.dto.ProductResponse;
import com.turkcell.productservice.application.exception.ProductNotFoundException;
import com.turkcell.productservice.application.mapper.ProductMapper;
import com.turkcell.productservice.domain.model.Money;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangePriceService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ChangePriceService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductResponse changePrice(ProductId productId, ChangePriceRequest request){
        Product product=repository.findById(productId).
                orElseThrow(()-> new ProductNotFoundException("Product not found"));

        Money newPrice=mapper.mapPrice(request);
        product.changePrice(newPrice);

        repository.save(product);

        return mapper.toResponse(product);

    }
}
