package org.hit.hradar.domain.user.command.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserAccountRequest {
    @NotBlank
    private String loginId;
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotNull
    private UserRole userRole;
    @NotNull
    private AccountStatus status;
}
