package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalScope;

import java.time.LocalDate;

@Getter
public class ResubmitGoalRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalScope scope;
    private Long actorId;//TODO: 인증 연동 후 제거
}

