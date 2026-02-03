package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.query.dto.response.GoalDetailResponseDto;
import org.hit.hradar.domain.goal.query.mapper.GoalDetailMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoalDetailQueryService {

    private final GoalDetailMapper goalDetailMapper;

    @Transactional(readOnly = true)
    public GoalDetailResponseDto getGoalDetail(Long goalId) {

        GoalDetailResponseDto response = goalDetailMapper.findGoalDetail(goalId);

        if (response == null) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_FOUND);
        }

        return response;
    }
}
