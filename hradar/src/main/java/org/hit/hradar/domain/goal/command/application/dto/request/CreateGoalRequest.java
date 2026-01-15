package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalScope;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;

import java.time.LocalDate;

@Getter
public class CreateGoalRequest {

    private Long parentGoalId;     // null → ROOT
    private GoalScope goalScope;   // ROOT면 TEAM만 허용
    private GoalType goalType;     // ROOT일 때만 사용
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long departmentId;     // ROOT에서만 사용
    private Long ownerId;
}

