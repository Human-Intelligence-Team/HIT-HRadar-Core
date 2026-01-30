package org.hit.hradar.domain.user.command.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangeMyPasswordRequest(

    @NotNull(message = "현재 비밀번호를 입력하십시오") @Size(max = 150)
    String currentPassword,
    @NotNull(message = "새로운 비밀번호를 입력하십시오") @Size(max = 150)
    String newPassword
) {}
