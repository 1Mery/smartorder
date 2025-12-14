package com.turkcell.orderservice.application.ports;

public interface DomainEventPublisher {
    void publish(Object event);
}
