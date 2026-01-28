package org.hit.hradar.domain.user.command.application.dto.request;

public record ChangeMyPasswordRequest(
    String currentPassword,
    String newPassword
) {}
