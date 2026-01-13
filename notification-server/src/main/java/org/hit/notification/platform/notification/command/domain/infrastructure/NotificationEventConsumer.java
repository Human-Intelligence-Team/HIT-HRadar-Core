package org.hit.notification.platform.notification.command.domain.infrastructure;

import lombok.RequiredArgsConstructor;
import org.hit.common.event.HrNotificationEvent;
import org.hit.notification.platform.notification.command.application.service.NotificationCommandService;
import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.hit.notification.platform.notification.sse.SseEmitterManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationCommandService commandService;
    private final SseEmitterManager emitterManager;

    @KafkaListener(topics = "hr.notification", groupId = "notification-group")
    public void consume(HrNotificationEvent event) {

        Notification notification = commandService.createFromEvent(event);

        emitterManager.send(
                notification.getUserId(),
                notification
        );
    }
}
