package org.hit.hradar.global.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationDTO {
    private final NotificationType type;
    private final Long userId;
    private final String title;
    private final String message;
    private final String linkUrl;
    private final Long createdBy;
}
