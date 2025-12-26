package com.turkcell.notificationservice.application.ports;

import com.turkcell.notificationservice.domain.model.Channel;
import com.turkcell.notificationservice.domain.model.Message;
import com.turkcell.notificationservice.domain.model.Recipient;

/**
 * amacımız usecase lerde gelen bildirim gönderme olayını soyutlamak
 */

public interface NotificationSenderPort {
    void send(Recipient recipient, Channel channel, Message message);
}
