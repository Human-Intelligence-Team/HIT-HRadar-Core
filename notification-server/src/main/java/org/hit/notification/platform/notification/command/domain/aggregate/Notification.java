package org.hit.notification.platform.notification.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    private Long id;

    /** 알림 대상 사용자 */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** Kafka eventId (멱등 처리용) */
    @Column(name = "event_id", nullable = false, length = 100)
    private String eventId;

    /** 알림 유형 (APPROVAL, NOTICE, HR 등) */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "is_read", nullable = false)
    private boolean read;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /* ===== Factory ===== */
    public static Notification create(
            String eventId,
            Long userId,
            String type,
            String title,
            String message,
            String linkUrl
    ) {
        Notification n = new Notification();
        n.eventId = eventId;
        n.userId = userId;
        n.type = type;
        n.title = title;
        n.message = message;
        n.linkUrl = linkUrl;
        n.read = false;
        n.createdAt = LocalDateTime.now();
        return n;
    }

    /* ===== Domain Behavior ===== */
    public void markAsRead() {
        this.read = true;
    }
}
