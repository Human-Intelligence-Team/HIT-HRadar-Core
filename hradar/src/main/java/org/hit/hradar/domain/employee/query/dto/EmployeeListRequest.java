package org.hit.hradar.domain.employee.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeListRequest {

  private Long deptId;
  private Long positionId;
  private String employeeName;
  private String keyword;

}
