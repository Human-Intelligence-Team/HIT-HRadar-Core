package org.hit.hradar.domain.employee.command.application.dto.reponse;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeAssignmentResponse {
  private Long empId;
  private Long deptId;
  private Long positionId;
  private String employeeNo;
  private Long movementId; // 부서/직위 변경 없으면 null
}
