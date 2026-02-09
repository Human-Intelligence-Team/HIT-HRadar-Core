package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
@Setter
@NoArgsConstructor
public class CompensationHistorySearchRequest {

  private String year;  // 년도
  private CompensationType type; // 변동 보상 타입
  private Long empId;
  private Long comId;

  public CompensationHistorySearchRequest(String year, CompensationType type, Long empId, Long comId) {
    this.year = year;
    this.type = type;
    this.empId = empId;
    this.comId = comId;
  }

  public void setEmpId(Long empId) {
    this.empId = empId;
  }

  public void setComId(Long comId) {
    this.comId = comId;
  }
}
