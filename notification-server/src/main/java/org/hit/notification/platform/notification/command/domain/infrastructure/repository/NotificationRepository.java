package org.hit.notification.platform.notification.command.domain.infrastructure.repository;

import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByEventId(String eventId);

    List<Notification> findByUserId(Long userId);

    @Modifying
    @Query("""
  update Notification n
  set n.read = true
  where n.userId = :userId
    and n.read = false
""")
    void markAllRead(@Param("userId") Long userId);
}
