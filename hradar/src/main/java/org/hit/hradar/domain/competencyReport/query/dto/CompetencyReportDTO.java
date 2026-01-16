package org.hit.hradar.domain.competencyReport.query.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;

@Getter
public class CompetencyReportDTO {

  private String cycleName; // 회차명
  private Integer year; // 년도
  private Quarter quarter;  // 분기
  private LocalDateTime createdAt;  // 생성일

  private Long competencyReportId;  // 역량 강화 리포트 ID

  private String employeeName;  // 사원 이름
  private String employeeNo;  // 사번
  private String deptName; // 부서명
  private String positionName; // 직위명

  private String kpiOkrResultSummary; //kpi okr
  private String goalFailureAnalysis; // 등급평가


  // 사원
  public CompetencyReportDTO (String cycleName, Integer year, Quarter quarter,LocalDateTime createdAt,Long competencyReportId ) {
    this.cycleName = cycleName;
    this.year = year;
    this.quarter = quarter;
    this.createdAt = createdAt;
    this.competencyReportId = competencyReportId;
  }

  // 팀장
  public CompetencyReportDTO (String cycleName, Integer year, Quarter quarter,LocalDateTime createdAt,Long competencyReportId
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

  public CompetencyReportDTO (String cycleName, Integer year, Quarter quarter,LocalDateTime createdAt,Long competencyReportId
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

}
