package com.turkcell.product_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/products")    
public class ProductsController {

    @GetMapping
    public String getAllProducts() {
        return "Hello World";
    }
    
}
