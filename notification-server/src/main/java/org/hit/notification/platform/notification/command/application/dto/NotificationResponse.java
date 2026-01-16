package org.hit.notification.platform.notification.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.notification.platform.notification.command.domain.aggregate.Notification;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private Long userId;

    private String type;
    private String title;
    private String message;
    private String linkUrl;

    private boolean read;
    private LocalDateTime createdAt;

    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(
                n.getId(),
                n.getUserId(),
                n.getType(),
                n.getTitle(),
                n.getMessage(),
                n.getLinkUrl(),
                n.isRead(),
                n.getCreatedAt()
        );
    }
}

