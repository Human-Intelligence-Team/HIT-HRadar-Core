package org.hit.hradar.domain.employee.command.application.dto.request;

import java.time.LocalDate;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeAssignmentRequest {

  private Long deptId;
  private Long positionId;
  private String employeeNo;

  // 이력용
  private String eventReason;            // nullable
  private LocalDate effectiveDate;       // null이면 LocalDate.now()
}
