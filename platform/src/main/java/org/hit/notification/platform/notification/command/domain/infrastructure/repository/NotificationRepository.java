package org.hit.notification.platform.notification.command.domain.infrastructure.repository;

import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByEventId(String eventId);

    List<Notification> findByUserId(Long userId);
}
