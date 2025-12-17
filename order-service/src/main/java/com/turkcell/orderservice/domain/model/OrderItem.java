package com.turkcell.orderservice.domain.model;

import com.turkcell.orderservice.domain.exception.OrderException;

import java.math.BigDecimal;

public class OrderItem {

    private final OrderItemId orderItemId;
    private final ProductId productId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal; // quantity * unitPrice

    private OrderItem(OrderItemId orderItemId,
                      ProductId productId,
                      int quantity,
                      BigDecimal unitPrice,
                      BigDecimal lineTotal) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public static OrderItem create(ProductId productId, int quantity, BigDecimal unitPrice) {
        validateProductId(productId);
        validateQuantity(quantity);
        validateUnitPrice(unitPrice);

        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        validateLineTotal(lineTotal, unitPrice, quantity);

        return new OrderItem(
                OrderItemId.generate(),
                productId,
                quantity,
                unitPrice,
                lineTotal
        );
    }

    public static OrderItem rehydrate(OrderItemId orderItemId,
                                      ProductId productId,
                                      int quantity,
                                      BigDecimal unitPrice,
                                      BigDecimal lineTotal) {

        validateProductId(productId);
        validateQuantity(quantity);
        validateUnitPrice(unitPrice);
        validateLineTotal(lineTotal, unitPrice, quantity);

        return new OrderItem(
                orderItemId,
                productId,
                quantity,
                unitPrice,
                lineTotal
        );
    }

    private static void validateProductId(ProductId productId) {
        if (productId == null) {
            throw new OrderException("ProductId cannot be null");
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new OrderException("Quantity must be greater than zero");
        }
    }

    private static void validateUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null) {
            throw new OrderException("Unit price cannot be null");

        }
        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderException("Unit price must be greater than zero");
        }
    }

    private static void validateLineTotal(BigDecimal lineTotal,
                                          BigDecimal unitPrice,
                                          int quantity) {
        if (lineTotal == null) {
            throw new OrderException("Line total cannot be null");
        }
        BigDecimal expected = unitPrice.multiply(BigDecimal.valueOf(quantity));
        if (!expected.equals(lineTotal)) {
            throw new OrderException("Line total is invalid");
        }
    }

    public OrderItemId getOrderItemId() {
        return orderItemId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }
}
