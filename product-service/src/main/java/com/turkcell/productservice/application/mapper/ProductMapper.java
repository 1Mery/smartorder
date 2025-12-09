package com.turkcell.productservice.application.mapper;

import com.turkcell.productservice.application.dto.*;
import com.turkcell.productservice.domain.model.Money;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(CreateProductRequest request){
        Money amount=Money.of(request.amount());
        Stock stock=Stock.of(request.stock());

        return Product.create(request.productName(),amount,stock);
    }

    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getProductId().value(),
                product.getProductName(),
                product.getAmount().amount(),
                product.getValue().value(),
                product.isActive()
        );
    }

    public Money mapPrice(ChangePriceRequest request){
        return  Money.of(request.newAmount());
    }

    public int mapStockAmount(IncreaseStockRequest request) {
        return request.stockAmount();
    }

    public int mapStockAmount(DecreaseStockRequest request) {
        return request.stockAmount();
    }
}
