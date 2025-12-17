package com.turkcell.orderservice.domain.model;

import com.turkcell.orderservice.domain.exception.OrderException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final OrderId orderId;

    private final CustomerId customerId;

    private final List<OrderItem> items = new ArrayList<>();

    private BigDecimal totalPrice = BigDecimal.ZERO; //tüm itemların toplamı

    private OrderStatus status;

    private Order(OrderId orderId, CustomerId customerId, OrderStatus status) {
        this.orderId = orderId;
        this.customerId = customerId;
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

        Order order = new Order(
                OrderId.generate(),
                customerId,
                OrderStatus.CREATED
        );

        order.addItem(productId, quantity, unitPrice); //totalPrice += lineTotal

        return order;
    }

    public static Order rehydrate(OrderId orderId,
                                  CustomerId customerId,
                                  List<OrderItem> items,
                                  OrderStatus status) {

        validateCustomerId(customerId);

        if (items == null || items.isEmpty()) {
            throw new OrderException("Order must have at least one item");
        }

        //Siparişi oluştur boş sepet gibi
        Order order = new Order(orderId, customerId, status);

        // listesine ekle
        order.items.addAll(items);

        //lineTotal'ından hesapla
        order.totalPrice = items.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return order;
    }

    //çoklu ürün siparişi için
    public void addItem(ProductId productId, int quantity, BigDecimal unitPrice) {
        if (this.status != OrderStatus.CREATED) {
            throw new OrderException("Items can only be added when order is in CREATED status");
        }

        OrderItem item = OrderItem.create(productId, quantity, unitPrice);
        this.items.add(item);
        //Toplam fiyatı güncelle
        this.totalPrice = this.totalPrice.add(item.getLineTotal());
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

    public void paid() {
        if (this.status != OrderStatus.CREATED) {
            throw new OrderException("Only CREATED orders can be approved");
        }

        this.status = OrderStatus.PAID;
    }

    public void delivered() {
        if (this.status != OrderStatus.PAID) {
            throw new OrderException("Only APPROVED orders can be completed");
        }

        this.status = OrderStatus.DELIVERED;
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERED) {
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

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
