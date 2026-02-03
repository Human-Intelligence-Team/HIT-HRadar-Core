package org.hit.hradar.domain.employee.query.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeMovementHistoryResponse {
  private Long movementId;
  private Long empId;
  private String empName;

  private Long fromDeptId;
  private Long toDeptId;

  private Long fromPositionId;
  private Long toPositionId;

  private String eventReason;
  private LocalDate effectiveDate;
  private LocalDateTime createdAt;
}
