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

  private int page = 1;
  private int size = 10;

  public int getPage() {
    return page < 1 ? 1 : page;
  }

  public int getSize() {
    return size < 1 ? 10 : size;
  }

  public int getOffset() {
    return (getPage() - 1) * getSize();
  }
}
