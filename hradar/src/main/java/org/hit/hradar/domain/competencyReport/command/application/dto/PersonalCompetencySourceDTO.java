package org.hit.hradar.domain.competencyReport.command.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCompetencySourceDTO {

  private Long comId;
  private Long ownerId; // 사원 (empId)
  private String employeeName;
  private Long departmentId; // 부서 Id
  private String departmentName;
  private String positionName;
  private Long cycleId;

  // 인사팀에서 정한 기준 날짜
  private LocalDate startDate;
  private LocalDate endDate;

  private List<KpiDataDTO> kpiList;
  private List<OkrDataDTO> okrList;


}
