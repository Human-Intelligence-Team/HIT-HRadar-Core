package org.hit.hradar.domain.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountResponse {
  private Long accId;
  private String loginId;
  private String password; // issueTempPassword=true일 때만 채움
}