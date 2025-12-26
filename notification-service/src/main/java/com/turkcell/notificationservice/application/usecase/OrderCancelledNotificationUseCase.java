package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.dto.OrderCancelledEventDto;
import com.turkcell.notificationservice.application.mapper.OrderCancelledMapper;
import com.turkcell.notificationservice.application.ports.NotificationSenderPort;
import com.turkcell.notificationservice.application.ports.ProcessedEventPort;
import com.turkcell.notificationservice.domain.model.*;

public class OrderCancelledNotificationUseCase {

    private final ProcessedEventPort processedEventPort;
    private final NotificationSenderPort notificationSenderPort;
    private final OrderCancelledMapper mapper;

    public OrderCancelledNotificationUseCase(ProcessedEventPort processedEventPort, NotificationSenderPort notificationSenderPort, OrderCancelledMapper mapper) {
        this.processedEventPort = processedEventPort;
        this.notificationSenderPort = notificationSenderPort;
        this.mapper = mapper;
    }

    public void sentCancel(OrderCancelledEventDto dto) {
        boolean sent = processedEventPort.tryMarkProcessed(dto.eventId(), "ORDER_CANCELLED", dto.orderId());
        if (!sent) {
            return;
        }
        Recipient recipient = mapper.toRecipient(dto);
        Message message = mapper.toMessage(dto);

        Notification notification = Notification.create
                (dto.eventId(), recipient, Channel.EMAIL, message);

        try {
            notificationSenderPort.send(recipient, Channel.EMAIL, message);
            notification.markSent();
        } catch (Exception ex) {
            notification.markFailed(ex.getMessage() == null ? "Notification send failed" : ex.getMessage());
            throw ex;
        }
    }
}
