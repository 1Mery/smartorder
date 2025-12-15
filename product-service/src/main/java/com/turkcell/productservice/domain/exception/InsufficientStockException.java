package com.turkcell.productservice.domain.exception;

public class InsufficientStockException extends ProductException {

    private final int requestedQuantity;
    private final int availableQuantity;

    public InsufficientStockException(String message, int requestedQuantity, int availableQuantity) {
        super(message);
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
