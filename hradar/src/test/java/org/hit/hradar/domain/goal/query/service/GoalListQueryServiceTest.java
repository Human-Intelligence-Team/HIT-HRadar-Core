package org.hit.hradar.domain.goal.query.service;

import org.hit.hradar.domain.goal.query.dto.response.GoalNodeResponseDto;
import org.hit.hradar.domain.goal.query.mapper.GoalMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalListQueryServiceTest {

    @InjectMocks
    private GoalListQueryService service;

    @Mock
    private GoalMapper goalMapper;

    @Test
    @DisplayName("부서별 목표 트리 조회 - 성공")
    void getGoalTree_success() {
        // given
        Long deptId = 1L;
        GoalNodeResponseDto goal = new GoalNodeResponseDto();
        // Reflection to set ID if no setter/builder available, assuming basic DTO
        // simplified setup for test
        
        when(goalMapper.selectGoals(deptId)).thenReturn(List.of(goal));
        when(goalMapper.selectKpisByGoalIds(anyList())).thenReturn(List.of());
        when(goalMapper.selectOkrsByGoalIds(anyList())).thenReturn(List.of());

        // when
        List<GoalNodeResponseDto> result = service.getGoalTree(deptId);

        // then
        assertThat(result).hasSize(1);
        verify(goalMapper).selectGoals(deptId);
    }

    @Test
    @DisplayName("내 목표 조회 - 성공")
    void getMyGoal_success() {
        // given
        Long empId = 100L;
        GoalNodeResponseDto goal = new GoalNodeResponseDto();
        
        when(goalMapper.selectMyGoals(empId)).thenReturn(List.of(goal));
        when(goalMapper.selectKpisByGoalIds(anyList())).thenReturn(List.of());
        when(goalMapper.selectOkrsByGoalIds(anyList())).thenReturn(List.of());

        // when
        List<GoalNodeResponseDto> result = service.getMyGoal(empId);

        // then
        assertThat(result).hasSize(1);
        verify(goalMapper).selectMyGoals(empId);
    }
}
