package org.hit.hradar.domain.user.command.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAdminAccountRequest {

    private Long accountId;
    private String loginId;
    private String tempPassword; // 1회 전달용


}
