package com.turkcell.notificationservice.application.ports;

import com.turkcell.notificationservice.domain.model.Channel;
import com.turkcell.notificationservice.domain.model.Message;
import com.turkcell.notificationservice.domain.model.Recipient;
import org.springframework.stereotype.Component;

/**
 * amacımız usecase lerde gelen bildirim gönderme olayını soyutlamak
 */
@Component
public interface NotificationSenderPort {
    void send(Recipient recipient, Channel channel, Message message);
}
