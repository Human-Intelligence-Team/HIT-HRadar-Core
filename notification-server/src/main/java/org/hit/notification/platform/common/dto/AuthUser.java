package org.hit.notification.platform.common.dto;

public record AuthUser(
        Long userId,
        String role,
        Long companyId
) {
}
