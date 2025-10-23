package com.turkcell.product_service.application.dto.response;

import java.util.List;

public record ProductListResponse(
    List<ProductResponse> products
) { }
