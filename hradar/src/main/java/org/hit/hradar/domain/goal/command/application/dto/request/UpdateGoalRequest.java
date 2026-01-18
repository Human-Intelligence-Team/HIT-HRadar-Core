package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalScope;

import java.time.LocalDate;

@Getter
public class UpdateGoalRequest {

    /*GoalType (KPI ↔ OKR 변경), GoalDepth, ParentGoalId 는 수정 불가*/
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalScope scope;
}
