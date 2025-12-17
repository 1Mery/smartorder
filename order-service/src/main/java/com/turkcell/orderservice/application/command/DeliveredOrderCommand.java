package com.turkcell.orderservice.application.command;

import java.util.UUID;

public record DeliveredOrderCommand(UUID orderId) {
}
