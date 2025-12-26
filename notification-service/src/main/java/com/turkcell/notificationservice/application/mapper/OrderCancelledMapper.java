package com.turkcell.notificationservice.application.mapper;

import com.turkcell.notificationservice.application.dto.OrderCancelledEventDto;
import com.turkcell.notificationservice.application.dto.OrderItemDto;
import com.turkcell.notificationservice.domain.model.Message;
import com.turkcell.notificationservice.domain.model.Recipient;
import org.springframework.stereotype.Component;

@Component
public class OrderCancelledMapper {
    public Recipient toRecipient(OrderCancelledEventDto dto) {
        return new Recipient(dto.email());
    }

    public Message toMessage(OrderCancelledEventDto dto) {
        // dto içindeki bilgileri kullanıcıya gidecek metne çeviriyoruz
        StringBuilder text = new StringBuilder();

        text.append("Order was being cancelled. OrderId: ").
                append(dto.orderId()).
                append("\n");


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
