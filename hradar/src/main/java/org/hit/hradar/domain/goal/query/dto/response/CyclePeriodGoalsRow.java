package org.hit.hradar.domain.goal.query.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.goal.command.domain.aggregate.AchieveStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalApproveStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalDepth;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalProgressStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalScope;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;

@Getter
public class CyclePeriodGoalsRow {

  // goal
  private Long goalId;
  private Long departmentId;
  private Long parentGoalId;
  private GoalDepth depth; // level 2, level3
  private GoalScope scope; // personal
  private GoalType type;  // kpi, okr

  private String title;
  private String description;

  private LocalDate startDate;
  private LocalDate endDate;

  private Long ownerId;
  private String employeeName;
  private String deptName;
  private String positionName;
  private GoalApproveStatus approveStatus; // APPROVED
  private GoalProgressStatus progressStatus;

  // kpi
  private String kpiMetricName;
  private BigDecimal kpiStartValue;
  private BigDecimal kpiTargetValue;

  // okr
  private String keyResultContent;
  private String okrMetricName;
  private BigDecimal keyTargetValue;
  private AchieveStatus isAchieved;

  public CyclePeriodGoalsRow(
      Long goalId,                    // 1 gl.goal_id
      Long departmentId,              // 2 gl.goal_dept_id
      GoalDepth depth,                // 3 gl.goal_depth
      GoalScope scope,                // 4 gl.goal_scope
      GoalType type,                  // 5 gl.goal_type
      String title,                   // 6 gl.goal_title
      String description,             // 7 gl.goal_description
      LocalDate startDate,            // 8 gl.goal_start_date
      LocalDate endDate,              // 9 gl.goal_end_date
      Long ownerId,                   // 10 gl.owner_id
      String employeeName,            // 11 emp.NAME
      String deptName,                // 12 dep.DEPT_NAME
      String positionName,            // 13 cp.NAME
      GoalApproveStatus approveStatus,// 14 gl.goal_approve_status
      GoalProgressStatus progressStatus, // 15 gl.goal_status
      String kpiMetricName,           // 16 kd.kpi_metric_name
      BigDecimal kpiStartValue,       // 17 kd.kpi_start_value
      BigDecimal kpiTargetValue,      // 18 kd.kpi_target_value
      String keyResultContent,        // 19 okr.key_result_content
      AchieveStatus isAchieved,       // 20 okr.is_achieved
      String okrMetricName,           // 21 okr.okr_metric_name
      BigDecimal keyTargetValue       // 22 okr.key_target_value
  ) {
    this.goalId = goalId;
    this.departmentId = departmentId;
    this.depth = depth;
    this.scope = scope;
    this.type = type;
    this.title = title;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.ownerId = ownerId;
    this.employeeName = employeeName;
    this.deptName = deptName;
    this.positionName = positionName;
    this.approveStatus = approveStatus;
    this.progressStatus = progressStatus;
    this.kpiMetricName = kpiMetricName;
    this.kpiStartValue = kpiStartValue;
    this.kpiTargetValue = kpiTargetValue;
    this.keyResultContent = keyResultContent;
    this.isAchieved = isAchieved;
    this.okrMetricName = okrMetricName;
    this.keyTargetValue = keyTargetValue;
  }


}
