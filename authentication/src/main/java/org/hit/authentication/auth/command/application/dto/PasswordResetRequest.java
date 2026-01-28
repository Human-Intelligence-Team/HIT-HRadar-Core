package org.hit.authentication.auth.command.application.dto;

public record PasswordResetRequest(
    String companyCode,
    String loginId
) {}

