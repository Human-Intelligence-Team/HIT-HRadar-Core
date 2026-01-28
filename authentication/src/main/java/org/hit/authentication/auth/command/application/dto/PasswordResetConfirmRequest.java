package org.hit.authentication.auth.command.application.dto;

public record PasswordResetConfirmRequest(
    String token,
    String newPassword
) {}
