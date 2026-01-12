package org.hit.notification.platform.consumer;

import org.hit.common.event.HrNotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationKafkaConsumer {

    @KafkaListener(
            topics = "hr.notification",
            groupId = "notification-group"
    )
    public void consume(HrNotificationEvent event) {
        System.out.println("📩 알림 수신");
        System.out.println(" - eventId: " + event.eventId());
        System.out.println(" - type: " + event.eventType());
        System.out.println(" - targetUserId: " + event.targetUserId());
        System.out.println(" - message: " + event.message());
    }
}
