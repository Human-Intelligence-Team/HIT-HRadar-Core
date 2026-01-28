package org.hit.hradar.domain.user.command.application.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFirstUserRequest {

    private Long accId;
    private Long comId;
    private String comCode;
    private Long empId;
    private String loginId;
    private String password;

}
