package com.turkcell.notificationservice.domain.repository;

import com.turkcell.notificationservice.domain.model.Channel;
import com.turkcell.notificationservice.domain.model.EventId;
import com.turkcell.notificationservice.domain.model.Notification;

import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findByEventId(EventId eventId); //tek kanal şimdilik email

    boolean existsByEventIdAndChannel(EventId eventId, Channel channel);

    //todo:ierde gönderilmemiş pending olanları bulup tekrar dene

}
