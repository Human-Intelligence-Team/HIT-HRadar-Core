package org.hit.hradar.domain.employee.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeListRequest {

  private Long deptId;
  private Long positionId;


}
