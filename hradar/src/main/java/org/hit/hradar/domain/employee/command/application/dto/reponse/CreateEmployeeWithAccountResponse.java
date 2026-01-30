package org.hit.hradar.domain.employee.command.application.dto.reponse;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeWithAccountResponse {

  private Long empId;
  private Long accId;

  private String employeeNo;
  private String loginId;
}
