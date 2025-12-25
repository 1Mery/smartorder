package com.turkcell.notificationservice.domain.model;

import com.turkcell.notificationservice.domain.exception.NotificationException;

import java.time.Instant;
import java.util.Objects;

public final class Notification {

    private final NotificationId id;
    private final EventId eventId;
    private final Recipient recipient;
    private final Channel channel;
    private final Message message;

    private NotificationStatus status;
    private final Instant createdAt;
    private Instant sentAt;
    private String failureReason;

    private Notification(
            NotificationId id,
            EventId eventId,
            Recipient recipient,
            Channel channel,
            Message message,
            NotificationStatus status,
            Instant createdAt,
            Instant sentAt,
            String failureReason
    ) {
        this.id = id;
        this.eventId = eventId;
        this.recipient = recipient;
        this.channel = Objects.requireNonNull(channel, "Channel cannot be null");
        this.message = message;
        this.status = Objects.requireNonNull(status, "NotificationStatus cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.sentAt = sentAt;
        this.failureReason = failureReason;
    }

    public static Notification create(EventId eventId, Recipient recipient, Channel channel, Message message) {
        return new Notification(
                NotificationId.generate(),
                eventId,
                recipient,
                channel,
                message,
                NotificationStatus.PENDING,
                Instant.now(),
                null,
                null
        );
    }

    public static Notification rehydrate(
            NotificationId id,
            EventId eventId,
            Recipient recipient,
            Channel channel,
            Message message,
            NotificationStatus status,
            Instant createdAt,
            Instant sentAt,
            String failureReason
    ) {
        return new Notification(id, eventId, recipient, channel, message, status, createdAt, sentAt, failureReason);
    }


    public void markSent() {
        ensurePending("Only PENDING notifications can be marked as SENT");

        Instant now = Instant.now();
        if (now.isBefore(createdAt)) {
            throw new NotificationException("sentAt cannot be before createdAt");
        }

        this.status = NotificationStatus.SENT;
        this.sentAt = now;
        this.failureReason = null;
    }

    public void markFailed(String reason) {
        ensurePending("Only PENDING notifications can be marked as FAILED");

        if (reason == null || reason.isBlank()) {
            throw new NotificationException("failureReason cannot be blank when FAILED");
        }

        this.status = NotificationStatus.FAILED;
        this.failureReason = reason;
        this.sentAt = null;
    }

    //kod tekrarını azaltmak için
    private void ensurePending(String message) {
        if (this.status != NotificationStatus.PENDING) {
            throw new NotificationException(message + ". Current status=" + this.status);
        }
    }

    public NotificationId getId() {
        return id;
    }

    public EventId getEventId() {
        return eventId;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Channel getChannel() {
        return channel;
    }

    public Message getMessage() {
        return message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
