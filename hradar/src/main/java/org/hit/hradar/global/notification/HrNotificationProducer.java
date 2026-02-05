package org.hit.hradar.global.notification;

import org.hit.common.event.HrNotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HrNotificationProducer {

    private final KafkaTemplate<String, HrNotificationEvent> kafkaTemplate;

    @org.springframework.beans.factory.annotation.Value("${spring.kafka.topic.notification}")
    private String topic;

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
                notificationDTO.getLinkUrl());

        kafkaTemplate.send(
                topic,
                String.valueOf(notificationDTO.getUserId()),
                event);
    }
}
