package org.hit.authentication.auth.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.hit.authentication.auth.command.domain.aggregate.Role;

@Getter
@Builder
public class TokenDTO {

    private Long userId;
    private Long companyId;
    private Role role;
}
