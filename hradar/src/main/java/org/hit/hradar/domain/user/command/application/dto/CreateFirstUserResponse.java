package org.hit.hradar.domain.user.command.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFirstUserResponse {

    private Long accId;
    private Long comId;
    private String comCode;
    private Long empId;
    private String loginId;
    private String password;

}
