package com.turkcell.orderservice.web.controller;

import com.turkcell.orderservice.application.command.ApproveOrderCommand;
import com.turkcell.orderservice.application.command.CancelOrderCommand;
import com.turkcell.orderservice.application.command.CompleteOrderCommand;
import com.turkcell.orderservice.application.command.CreateOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.handler.ApproveOrderCommandHandler;
import com.turkcell.orderservice.application.handler.CancelOrderCommandHandler;
import com.turkcell.orderservice.application.handler.CompleteOrderCommandHandler;
import com.turkcell.orderservice.application.handler.CreateOrderCommandHandler;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CreateOrderCommandHandler createHandler;
    private final CancelOrderCommandHandler cancelHandler;
    private final ApproveOrderCommandHandler approveHandler;
    private final CompleteOrderCommandHandler completeHandler;

    public OrderController(CreateOrderCommandHandler createHandler,
                           CancelOrderCommandHandler cancelHandler,
                           ApproveOrderCommandHandler approveHandler,
                           CompleteOrderCommandHandler completeHandler) {
        this.createHandler = createHandler;
        this.cancelHandler = cancelHandler;
        this.approveHandler = approveHandler;
        this.completeHandler = completeHandler;
    }

    @PostMapping
    public OrderResponse create(@RequestBody CreateOrderCommand request) {
        return createHandler.create(request);
    }

    @PostMapping("/{orderId}/cancel")
    public OrderResponse cancel(@PathVariable UUID orderId) {
        return cancelHandler.cancelOrder(new CancelOrderCommand(orderId));
    }

    @PostMapping("/{orderId}/approve")
    public OrderResponse approve(@PathVariable UUID orderId) {
        return approveHandler.approveOrder(new ApproveOrderCommand(orderId));
    }

    @PostMapping("/{orderId}/complete")
    public OrderResponse complete(@PathVariable UUID orderId) {
        return completeHandler.completeOrder(new CompleteOrderCommand(orderId));
    }
}
