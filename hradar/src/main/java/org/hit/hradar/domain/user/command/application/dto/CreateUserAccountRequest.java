package org.hit.hradar.domain.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserAccountRequest {

  private String loginId;
  private UserRole userRole;
  private String empId;
  private boolean issueTempPassword;

}
