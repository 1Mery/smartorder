package com.turkcell.orderservice.infrastructure.kafka.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.Instant;
import java.util.List;

/*/
Db' deki pending kayıtları alarak kafkaya göndermek
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisherJob {


    private final OutboxEventRepository outboxEventRepository;
    private final StreamBridge streamBridge;

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void publishPending() {
        List<OutboxEventEntity> events =
                outboxEventRepository.findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        for (OutboxEventEntity eventEntity : events) {
            try {
                boolean sent = streamBridge.send(eventEntity.getBindingName(), eventEntity.getPayload());
                if (!sent) {
                    log.warn("StreamBridge send returned false. eventId={}", eventEntity.getEventId());
                    continue;
                }
                eventEntity.setStatus(OutboxStatus.SENT);
                eventEntity.setSentAt(Instant.now());

            } catch (Exception ex) {
                log.error("Outbox publish failed. eventId={}, orderId={}", eventEntity.getEventId(), eventEntity.getOrderId(), ex);

            }
        }
    }
}

