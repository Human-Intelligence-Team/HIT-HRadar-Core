package org.hit.hradar.domain.competencyReport.command.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalApproveStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalDepth;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalProgressStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KpiDataDTO {
  private Long goalId;
  private Long parentGoalId; // 상위 목표
  private GoalDepth depth;
  private GoalType type;

  private String title;
  private String description;

  // kpi 작성 기간
  private LocalDate startDate;
  private LocalDate endDate;

  private GoalApproveStatus approveStatus; // APPROVED
  private GoalProgressStatus progressStatus;

  private String kpiMetricName;
  private BigDecimal kpiStartValue;
  private BigDecimal kpiTargetValue;

}
