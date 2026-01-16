package org.hit.common.event;

public record HrNotificationEvent(
        String eventId,
        String type,
        Long userId,
        String title,
        String message,
        String linkUrl
) {}

