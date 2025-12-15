package com.turkcell.productservice.web.controller;

import com.turkcell.productservice.application.dto.*;
import com.turkcell.productservice.application.services.*;
import com.turkcell.productservice.domain.model.ProductId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final CreateProductService createProductService;
    private final GetProductByIdService getProductByIdService;
    private final RenameProductService renameProductService;
    private final ChangePriceService changePriceService;
    private final IncreaseStockService increaseStockService;
    private final DecreaseStockService decreaseStockService;
    private final GetProductOrderInfoService getProductOrderInfoService;

    public ProductController(CreateProductService createProductService, GetProductByIdService getProductByIdService, RenameProductService renameProductService, ChangePriceService changePriceService, IncreaseStockService increaseStockService, DecreaseStockService decreaseStockService, GetProductOrderInfoService getProductOrderInfoService) {
        this.createProductService = createProductService;
        this.getProductByIdService = getProductByIdService;
        this.renameProductService = renameProductService;
        this.changePriceService = changePriceService;
        this.increaseStockService = increaseStockService;
        this.decreaseStockService = decreaseStockService;
        this.getProductOrderInfoService = getProductOrderInfoService;
    }

    @PostMapping
    public ProductResponse create(@RequestBody CreateProductRequest request){
        return createProductService.create(request);
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable UUID id) {
        return getProductByIdService.get(new ProductId(id));
    }

    @PutMapping("/{productId}/rename")
    public ProductResponse renameName(@PathVariable UUID productId,
                                      @RequestBody RenameProductRequest request){
        return renameProductService.renameName(new ProductId(productId),request);
    }

    @PutMapping("/{productId}/price")
    public ProductResponse changePrice(@PathVariable UUID productId,
                                       @RequestBody ChangePriceRequest request){
        return changePriceService.changePrice(new ProductId(productId),request);
    }

    @PutMapping("/{productId}/stock/increase")
    public ProductResponse increase(@PathVariable UUID productId,
                                    @RequestBody IncreaseStockRequest request){
        return increaseStockService.increase(new ProductId(productId),request);
    }

    @PutMapping("/{productId}/stock/decrease")
    public ProductResponse decrease(@PathVariable UUID productId,
                                    @RequestBody DecreaseStockRequest request){
        return decreaseStockService.decrease(new ProductId(productId),request);
    }

    //Sadece order service için: minimal fiyat + stok bilgisi
    @GetMapping("/{productId}/order-info")
    public ProductOrderInfoResponse getOrderInfo(@PathVariable UUID productId) {
        return getProductOrderInfoService.get(new ProductId(productId));
    }

}
