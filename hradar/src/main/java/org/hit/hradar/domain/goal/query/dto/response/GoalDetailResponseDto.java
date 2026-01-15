package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalApproveStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalDepth;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalScope;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GoalDetailResponseDto {

    private Long goalId;
    private Long parentGoalId;

    private GoalDepth depth;
    private GoalScope scope;
    private GoalType type;

    private String title;
    private String description;

    private GoalApproveStatus approveStatus;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long departmentId;
    private BigDecimal progressRate;
}
