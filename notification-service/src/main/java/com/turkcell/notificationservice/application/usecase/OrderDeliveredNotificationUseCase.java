package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.dto.OrderDeliveredEventDto;
import com.turkcell.notificationservice.application.mapper.OrderDeliveredMapper;
import com.turkcell.notificationservice.application.ports.NotificationSenderPort;
import com.turkcell.notificationservice.application.ports.ProcessedEventPort;
import com.turkcell.notificationservice.domain.model.Channel;
import com.turkcell.notificationservice.domain.model.Message;
import com.turkcell.notificationservice.domain.model.Notification;
import com.turkcell.notificationservice.domain.model.Recipient;
import org.springframework.stereotype.Service;

@Service
public class OrderDeliveredNotificationUseCase {

    private final ProcessedEventPort processedEventPort;
    private final NotificationSenderPort notificationSenderPort;
    private final OrderDeliveredMapper mapper;

    public OrderDeliveredNotificationUseCase(ProcessedEventPort processedEventPort, NotificationSenderPort notificationSenderPort, OrderDeliveredMapper mapper) {
        this.processedEventPort = processedEventPort;
        this.notificationSenderPort = notificationSenderPort;
        this.mapper = mapper;
    }

    public void sentDelivered(OrderDeliveredEventDto dto) {
        boolean send = processedEventPort.tryMarkProcessed(dto.eventId(), "ORDER_DELIVERED", dto.orderId());
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
