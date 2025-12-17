package com.turkcell.orderservice.web.controller;

import com.turkcell.orderservice.application.command.*;
import com.turkcell.orderservice.application.dto.CreateOrderRequest;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.handler.PaidOrderCommandHandler;
import com.turkcell.orderservice.application.handler.CancelOrderCommandHandler;
import com.turkcell.orderservice.application.handler.DeliveredOrderCommandHandler;
import com.turkcell.orderservice.application.handler.CreateOrderCommandHandler;
import com.turkcell.orderservice.application.query.GetOrderByIdHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CreateOrderCommandHandler createHandler;
    private final CancelOrderCommandHandler cancelHandler;
    private final PaidOrderCommandHandler paidHandler;
    private final DeliveredOrderCommandHandler deliverHandler;
    private final GetOrderByIdHandler handler;

    public OrderController(CreateOrderCommandHandler createHandler, CancelOrderCommandHandler cancelHandler, PaidOrderCommandHandler paidHandler, DeliveredOrderCommandHandler deliverHandler, GetOrderByIdHandler handler) {
        this.createHandler = createHandler;
        this.cancelHandler = cancelHandler;
        this.paidHandler = paidHandler;
        this.deliverHandler = deliverHandler;
        this.handler = handler;
    }

    @PostMapping
    public OrderResponse create(@RequestBody CreateOrderRequest request) {
        List<CreateOrderCommand.CreateOrderItemCommand> itemCommands = new ArrayList<>();

        if (request.items() != null) {
            for (CreateOrderRequest.CreateOrderItemRequest itemRequest : request.items()) {
                itemCommands.add(
                        new CreateOrderCommand.CreateOrderItemCommand(
                                itemRequest.productId(),
                                itemRequest.quantity()
                        )
                );
            }
        }
        CreateOrderCommand command = new CreateOrderCommand(
                request.customerId(),
                itemCommands
        );
        return createHandler.create(command);
    }

    @PostMapping("/{orderId}/cancel")
    public OrderResponse cancel(@PathVariable UUID orderId) {
        return cancelHandler.cancelOrder(new CancelOrderCommand(orderId));
    }

    @PostMapping("/{orderId}/paid")
    public OrderResponse approve(@PathVariable UUID orderId) {
        return paidHandler.paidOrder(new PaidOrderCommand(orderId));
    }

    @PostMapping("/{orderId}/delivered")
    public OrderResponse complete(@PathVariable UUID orderId) {
        return deliverHandler.deliveredOrder(new DeliveredOrderCommand(orderId));
    }

    @GetMapping("{orderId}")
    public OrderResponse getOrder(@PathVariable UUID orderId){
        return handler.getOrder(new GetOrderByIdCommand(orderId));
    }
}
