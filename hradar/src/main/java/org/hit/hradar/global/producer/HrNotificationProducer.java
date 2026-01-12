package org.hit.hradar.global.producer;

import org.hit.common.event.HrNotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class HrNotificationProducer {

    private final KafkaTemplate<String, HrNotificationEvent> kafkaTemplate;

    public HrNotificationProducer(KafkaTemplate<String, HrNotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Long userId, String message) {

        HrNotificationEvent event = new HrNotificationEvent(
                UUID.randomUUID().toString(),     // eventId
                "REPORT_CREATED",                 // eventType
                userId,                           // targetUserId
                message                           // message
        );

        kafkaTemplate.send(
                "hr.notification",
                String.valueOf(userId),
                event
        );
    }
}
