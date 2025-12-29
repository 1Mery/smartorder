package com.turkcell.notificationservice.application.mapper;

import com.turkcell.notificationservice.application.dto.OrderCreatedEventDto;
import com.turkcell.notificationservice.application.dto.OrderItemDto;
import com.turkcell.notificationservice.domain.model.Message;
import com.turkcell.notificationservice.domain.model.Recipient;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedMapper {

    public Recipient toRecipient(OrderCreatedEventDto dto) {
        return new Recipient(dto.email());
    }

    public Message toMessage(OrderCreatedEventDto dto) {
        StringBuilder text = new StringBuilder();

        text.append("Order created. OrderId: ")
                .append(dto.orderId())
                .append("\n")
                .append("CustomerId: ")
                .append(dto.customerId())
                .append("\n");

        if (dto.items() != null && !dto.items().isEmpty()) {
            text.append("Products:\n");
            for (OrderItemDto item : dto.items()) {
                text.append(item.productId())
                        .append(" x").append(item.quantity())
                        .append(" -> sum=").append(item.lineTotal())
                        .append("\n");
            }
        }

        return new Message(text.toString());
    }
}
