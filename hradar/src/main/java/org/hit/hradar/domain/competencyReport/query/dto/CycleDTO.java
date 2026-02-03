package org.hit.hradar.domain.competencyReport.query.dto;

import java.time.LocalDate;

import lombok.Getter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus;

@Getter
public class CycleDTO {

  private Long cycleId;
  private String year;
  private Quarter quarter;
  private String cycleName;
  private LocalDate startDate;
  private LocalDate endDate;
  private CycleStatus status;
  private String empName;   // 담당자 이름
  private Character isReportGenerated;

  public CycleDTO(Long cycleId, String year, Quarter quarter, String cycleName, LocalDate startDate, LocalDate endDate, CycleStatus status, String empName,  Character isReportGenerated) {
    this.cycleId = cycleId;
    this.year = year;
    this.quarter = quarter;
    this.cycleName = cycleName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.empName = empName;
    this.isReportGenerated = isReportGenerated;

  }

}
