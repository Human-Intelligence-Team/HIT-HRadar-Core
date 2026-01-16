package org.hit.notification.platform.notification.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.common.event.HrNotificationEvent;
import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.hit.notification.platform.notification.command.domain.infrastructure.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationCommandService {

    private final NotificationRepository repository;

    public Notification createFromEvent(HrNotificationEvent event) {

        if (repository.existsByEventId(event.eventId())) {
            return null; // 멱등 처리
        }

        Notification notification = Notification.create(
                event.eventId(),
                event.userId(),
                event.type(),
                event.title(),
                event.message(),
                event.linkUrl()
        );

        repository.save(notification);

        return notification;
    }

    public void markAsRead(Long id) {
        Notification n = repository.findById(id)
                .orElseThrow();
        n.markAsRead();
    }

    public void markAsReadAll(Long userId) {
        repository.markAllRead(userId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}