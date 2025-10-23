package com.turkcell.product_service.application.mapper;

import com.turkcell.product_service.domain.entity.Product;
import com.turkcell.product_service.domain.entity.Product.ProductId;
import com.turkcell.product_service.domain.valueobjects.Price;
import com.turkcell.product_service.domain.valueobjects.Stock;
import com.turkcell.product_service.domain.valueobjects.Currency;
import com.turkcell.product_service.application.dto.request.CreateProductRequest;
import com.turkcell.product_service.application.dto.request.UpdateProductRequest;
import com.turkcell.product_service.application.dto.response.ProductResponse;
import com.turkcell.product_service.application.dto.response.ProductListResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductMapper {

    // CreateProductRequest -> Product
    public static Product toDomain(CreateProductRequest request) {
        return Product.create(
                request.name(),
                request.description(),
                request.amount(),
                request.quantity(),
                request.currencyName(),
                request.code(),
                request.symbol()               
        );
    }

    // UpdateProductRequest -> Product
    public static Product toDomain(UpdateProductRequest request) {
        return Product.reconstruct(
            ProductId.fromUUID(id),
            request.name(),
            request.description(),
            request.amount(),
            request.quantity(),
            request.currencyName(),
            request.code(),
            request.symbol()
    );
    }

    // Product -> ProductResponse
    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId().getValue(),         
                product.getName(),
                product.getDescription(),
                product.getPrice().getAmount(),      
                product.getStock().getQuantity(),   
                product.getCurrency().getCode(),
                product.getCurrency().getSymbol(),
                product.getCurrency().getName()    
        );
    }

    // List<Product> -> ProductListResponse
    public static ProductListResponse toListResponse(List<Product> products) {
        List<ProductResponse> list = products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
        return new ProductListResponse(list);
    }
}
