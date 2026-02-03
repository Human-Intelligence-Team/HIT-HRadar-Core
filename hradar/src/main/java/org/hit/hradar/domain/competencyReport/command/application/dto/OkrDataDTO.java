package org.hit.hradar.domain.competencyReport.command.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.goal.command.domain.aggregate.AchieveStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalApproveStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalDepth;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalProgressStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;

@Getter
@AllArgsConstructor
public class OkrDataDTO {

  private Long goalId;
  private Long parentGoalId; // 상위 목표
  private GoalDepth depth;
  private GoalType type;

  private String title;
  private String description;

  // okr 작성 기간
  private LocalDate startDate;
  private LocalDate endDate;

  private GoalApproveStatus approveStatus; // APPROVED
  private GoalProgressStatus progressStatus;

  private String keyResultContent;
  private String okrMetricName;
  private BigDecimal keyTargetValue;
  private AchieveStatus isAchieved;

}
