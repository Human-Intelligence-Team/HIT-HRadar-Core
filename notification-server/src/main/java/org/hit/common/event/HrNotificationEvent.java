package org.hit.common.event;

public record HrNotificationEvent(
        String eventId,
        String eventType,
        Long targetUserId,
        String message
) {}
