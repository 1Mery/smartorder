package com.turkcell.orderservice.domain.model;

import com.turkcell.orderservice.domain.exception.OrderException;

import java.math.BigDecimal;

public class Order {
    private final OrderId orderId;

    private final CustomerId customerId;

    private final ProductId productId;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;  //quantity * unitPrice

    private OrderStatus status;

    private Order(OrderId orderId, CustomerId customerId, ProductId productId, int quantity, BigDecimal unitPrice, BigDecimal totalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public static Order create(CustomerId customerId,
                               ProductId productId,
                               int quantity,
                               BigDecimal unitPrice) {

        validateCustomerId(customerId);
        validateProductId(productId);
        validateQuantity(quantity);
        validateUnitPrice(unitPrice);

        //totalPrice hesapla
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

        validateTotalPrice(totalPrice, unitPrice, quantity);

        return new Order(
                OrderId.generate(),
                customerId,
                productId,
                quantity,
                unitPrice,
                totalPrice,
                OrderStatus.CREATED
        );
    }

    public static Order rehydrate(OrderId orderId,
                                  CustomerId customerId,
                                  ProductId productId,
                                  int quantity,
                                  BigDecimal unitPrice,
                                  BigDecimal totalPrice,
                                  OrderStatus status) {

        return new Order(orderId,customerId,productId,quantity,unitPrice,totalPrice,status);
    }

    private static void validateCustomerId(CustomerId customerId) {
        if (customerId == null)
            throw new OrderException("CustomerId cannot be null");
    }

    private static void validateProductId(ProductId productId) {
        if (productId == null)
            throw new OrderException("ProductId cannot be null");
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0)
            throw new OrderException("Quantity must be greater than zero");
    }

    private static void validateUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null)
            throw new OrderException("Unit price cannot be null");

        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new OrderException("Unit price must be greater than zero");
    }

    private static void validateTotalPrice(BigDecimal totalPrice, BigDecimal unitPrice, int quantity) {
        if (totalPrice == null)
            throw new OrderException("Total price cannot be null");

        BigDecimal expected = unitPrice.multiply(BigDecimal.valueOf(quantity));
        if (!expected.equals(totalPrice))
            throw new OrderException("Total price is invalid");
    }

    public void approve() {
        if (this.status != OrderStatus.CREATED) {
            throw new OrderException("Only CREATED orders can be approved");
        }

        this.status = OrderStatus.APPROVED;
    }

    public void complete() {
        if (this.status != OrderStatus.APPROVED) {
            throw new OrderException("Only APPROVED orders can be completed");
        }

        this.status = OrderStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new OrderException("COMPLETED orders cannot be cancelled");
        }

        this.status = OrderStatus.CANCELLED;
    }

    public void reject() {
        if (this.status != OrderStatus.CREATED) {
            throw new OrderException("Only CREATED orders can be rejected");
        }

        this.status = OrderStatus.CANCELLED;
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public CustomerId getCustomerId() {
        return customerId;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
