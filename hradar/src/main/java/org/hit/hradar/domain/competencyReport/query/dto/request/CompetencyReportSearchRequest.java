package org.hit.hradar.domain.competencyReport.query.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;

@Getter
@NoArgsConstructor
@Setter
public class CompetencyReportSearchRequest {

  private Long empId;
  private Long comId;

  private String year;
  private Quarter quarter;

  private Long deptId;  // 부서
  private Long comPositionId;  // 직급
  private Long employeeNo; // 사번
  private String employeeName; // 사원명


  public CompetencyReportSearchRequest (String year) {
    this.year = year;
  }
  public CompetencyReportSearchRequest(String year, Quarter quarter, Long deptId, Long comPositionId, Long employeeNo
    , String employeeName
  ) {
    this.year = year;
    this.quarter = quarter;
    this.deptId = deptId;
    this.comPositionId = comPositionId;
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;
  }


}
