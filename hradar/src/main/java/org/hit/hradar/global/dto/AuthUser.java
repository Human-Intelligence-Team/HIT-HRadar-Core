package org.hit.hradar.global.dto;

public record AuthUser(
        Long userId,
        String role,
        Long companyId
) {
}
