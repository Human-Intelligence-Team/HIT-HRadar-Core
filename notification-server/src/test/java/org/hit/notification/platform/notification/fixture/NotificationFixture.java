package org.hit.notification.platform.notification.fixture;

import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class NotificationFixture {

    public static Notification unread(Long id, LocalDateTime createdAt) {
        Notification notification = Notification.create(
                "event-" + id,
                1L,
                "HR",
                "title-" + id,
                "message-" + id,
                "/link"
        );

        // createdAt 강제 세팅 (리플렉션 or 테스트용 setter)
        ReflectionTestUtils.setField(notification, "createdAt", createdAt);

        return notification;
    }

    public static Notification unread(Long id) {
        return unread(id, LocalDateTime.now());
    }
}
