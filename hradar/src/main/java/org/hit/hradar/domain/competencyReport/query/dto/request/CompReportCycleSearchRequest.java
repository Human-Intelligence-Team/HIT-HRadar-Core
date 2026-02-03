package org.hit.hradar.domain.competencyReport.query.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;

@Getter
@Setter
public class CompReportCycleSearchRequest {

  private String year;
  private Quarter quarter;

  private Long cycleId;
  private Long deptId;
  private Long comPositionId;
  private String employeeNo;
  private String employeeName;

  public CompReportCycleSearchRequest(String year, Quarter quarter, Long cycleId, Long deptId, Long comPositionId, String employeeNo, String employeeName) {
    this.year = year;
    this.quarter = quarter;
    this.cycleId = cycleId;
    this.deptId = deptId;
    this.comPositionId = comPositionId;
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;
  }

}
