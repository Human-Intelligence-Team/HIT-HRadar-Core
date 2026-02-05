package org.hit.hradar.domain.competencyReport.query.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus;

@Getter
@Setter
@NoArgsConstructor
public class CompReportCycleSearchRequest {

  private String year;
  private Quarter quarter;

  private Long cycleId;
  private String cycleName;
  private Long deptId;
  private Long comPositionId;
  private String employeeNo;
  private String employeeName;

  private Character isCompReportGenerated;
  private CycleStatus status;

  private Long comId;
  public CompReportCycleSearchRequest(String year, Quarter quarter, Long cycleId, Long deptId, Long comPositionId, String employeeNo, String employeeName) {
    this.year = year;
    this.quarter = quarter;
    this.cycleId = cycleId;
    this.deptId = deptId;
    this.comPositionId = comPositionId;
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;
  }

  public CompReportCycleSearchRequest(Long cycleId, String cycleName, Long empId, String employeeName,
      Character isCompReportGenerated, Quarter quarter ,CycleStatus status, String year) {
    this.cycleId = cycleId;
    this.cycleName = cycleName;
    this.deptId = empId;
    this.employeeName = employeeName;
    this.isCompReportGenerated = isCompReportGenerated;
    this.quarter = quarter;
    this.status = status;
    this.year = year;

  }

  public CompReportCycleSearchRequest(Long comId, String year, Quarter quarter, Long cycleId) {
    this.comId = comId;
    this.year = year;
    this.quarter = quarter;
    this.cycleId = cycleId;
  }
}
