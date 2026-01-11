package org.hit.notification.platform.event;

public record NotificationEvent(
        String eventType,
        Long targetUserId,
        String message
) {}
