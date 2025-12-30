package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.dto.OrderPaidEventDto;
import com.turkcell.notificationservice.application.mapper.OrderPaidMapper;
import com.turkcell.notificationservice.application.ports.NotificationSenderPort;
import com.turkcell.notificationservice.application.ports.ProcessedEventPort;
import com.turkcell.notificationservice.domain.model.Channel;
import com.turkcell.notificationservice.domain.model.Message;
import com.turkcell.notificationservice.domain.model.Notification;
import com.turkcell.notificationservice.domain.model.Recipient;
import org.springframework.stereotype.Service;

@Service
public class OrderPaidNotificationUseCase {
    private final ProcessedEventPort processedEventPort;
    private final NotificationSenderPort notificationSenderPort;
    private final OrderPaidMapper mapper;

    public OrderPaidNotificationUseCase(ProcessedEventPort processedEventPort, NotificationSenderPort notificationSenderPort, OrderPaidMapper mapper) {
        this.processedEventPort = processedEventPort;
        this.notificationSenderPort = notificationSenderPort;
        this.mapper = mapper;
    }

    public void sentPaid(OrderPaidEventDto dto) {
        boolean send = processedEventPort.tryMarkProcessed(dto.eventId(), "ORDER_PAID", dto.orderId());
        if (!send) {
            return;
        }
        Recipient recipient = mapper.toRecipient(dto);
        Message message = mapper.toMessage(dto);

        Notification notification = Notification.create(
                dto.eventId(),
                recipient,
                Channel.EMAIL,
                message
        );

        try {
            notificationSenderPort.send(recipient, Channel.EMAIL, message);
            notification.markSent();
        } catch (Exception e) {
            notification.markFailed(e.getMessage() == null ? "Notification send failed" : e.getMessage());
            throw e;
        }
    }
}

