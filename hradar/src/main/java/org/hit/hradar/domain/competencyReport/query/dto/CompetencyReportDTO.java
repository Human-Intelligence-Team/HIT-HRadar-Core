package org.hit.hradar.domain.competencyReport.query.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus;

@Getter
@NoArgsConstructor
public class CompetencyReportDTO {

  private Long cycleId;
  private String cycleName; // 회차명
  private String year; // 년도
  private Quarter quarter;  // 분기
  private String createdAt;  // 생성일

  private Long competencyReportId;  // 역량 강화 리포트 ID
  private Character isCompReportGenerated;  // 역량 강화 리포트 ID

  private Long employeeId;
  private String employeeName;  // 사원 이름
  private String employeeNo;  // 사번
  private String deptName; // 부서명
  private String positionName; // 직위명

  private String kpiOkrResultSummary; //kpi okr
  private String goalFailureAnalysis; // 등급평가

  private CycleStatus status;
  private String startDate;


  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  private String endDate;

  // 사원
  public CompetencyReportDTO (String cycleName, String year, Quarter quarter,String createdAt,Long competencyReportId ) {
    this.cycleName = cycleName;
    this.year = year;
    this.quarter = quarter;
    this.createdAt = createdAt;
    this.competencyReportId = competencyReportId;
  }

  // 팀장
  public CompetencyReportDTO (String cycleName, String year, Quarter quarter,String createdAt,Long competencyReportId
      , String employeeName, String employeeNo, String deptName, String positionName) {
    this.cycleName = cycleName;
    this.year = year;
    this.quarter = quarter;
    this.createdAt = createdAt;
    this.competencyReportId = competencyReportId;
    this.employeeName = employeeName;
    this.employeeNo = employeeNo;
    this.deptName = deptName;
    this.positionName = positionName;

  }

  public CompetencyReportDTO (String cycleName, String year, Quarter quarter,String createdAt,Long competencyReportId
      , String employeeName, String employeeNo, String deptName, String positionName, String kpiOkrResultSummary, String goalFailureAnalysis) {
    this.cycleName = cycleName;
    this.year = year;
    this.quarter = quarter;
    this.createdAt = createdAt;
    this.competencyReportId = competencyReportId;
    this.employeeName = employeeName;
    this.employeeNo = employeeNo;
    this.deptName = deptName;
    this.positionName = positionName;
    this.kpiOkrResultSummary = kpiOkrResultSummary;
    this.goalFailureAnalysis = goalFailureAnalysis;
  }

  public CompetencyReportDTO (Long cycleId, String cycleName, Long empId ,String employeeName, Character isCompReportGenerated,
      Quarter quarter, CycleStatus status, String year) {
    this.cycleId = cycleId;
    this.cycleName = cycleName;
    this.employeeId = empId;
    this.employeeName = employeeName;
    this.isCompReportGenerated = isCompReportGenerated;
    this.year = year;
    this.quarter = quarter;
    this.status = status;

  }

  public CompetencyReportDTO (String startDate, String endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

}
