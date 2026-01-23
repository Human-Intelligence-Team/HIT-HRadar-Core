package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
public class CompensationHistorySearchRequest {

  private String year;  // 년도
  private CompensationType type; // 변동 보상 타입
  private Long empId;

  public CompensationHistorySearchRequest(String year, CompensationType type, Long empId) {
    this.year = year;
    this.type = type;
    this.empId = empId;
  }

  public void setEmpId(Long empId) {
    this.empId = empId;
  }

}
