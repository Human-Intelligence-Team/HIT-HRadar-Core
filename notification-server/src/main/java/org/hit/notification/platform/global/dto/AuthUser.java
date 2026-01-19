package org.hit.notification.platform.global.dto;

public record AuthUser(
        Long userId,
        String role,
        Long companyId,
        Long employeeId
) {
}
