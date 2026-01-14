package org.hit.hradar.domain.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserAccountResponse {
  private Long accId;
  private String loginId;
  private boolean mustChangePassword;
  private String tempPassword; // issueTempPassword=true일 때만 채움
}