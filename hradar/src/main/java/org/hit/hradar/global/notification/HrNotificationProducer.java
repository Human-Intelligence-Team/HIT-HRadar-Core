package org.hit.hradar.global.notification;

import org.hit.common.event.HrNotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HrNotificationProducer {

    private final KafkaTemplate<String, HrNotificationEvent> kafkaTemplate;

    public HrNotificationProducer(KafkaTemplate<String, HrNotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationDTO notificationDTO) {

        HrNotificationEvent event = new HrNotificationEvent(
                UUID.randomUUID().toString(),
                notificationDTO.getType().name(),
                notificationDTO.getUserId(),
                notificationDTO.getTitle(),
                notificationDTO.getMessage(),
                notificationDTO.getLinkUrl()
        );

        kafkaTemplate.send(
                "hr.notification",
                String.valueOf(notificationDTO.getUserId()),
                event
        );
    }
}
