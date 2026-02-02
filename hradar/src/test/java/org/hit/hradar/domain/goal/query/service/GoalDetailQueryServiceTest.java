package org.hit.hradar.domain.goal.query.service;

import org.hit.hradar.domain.goal.query.dto.response.GoalDetailResponseDto;
import org.hit.hradar.domain.goal.query.mapper.GoalDetailMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalDetailQueryServiceTest {

    @InjectMocks
    private GoalDetailQueryService service;

    @Mock
    private GoalDetailMapper goalDetailMapper;

    @Test
    @DisplayName("목표 상세 조회 - 성공")
    void getGoalDetail_success() {
        // given
        Long goalId = 1L;
        GoalDetailResponseDto responseDto = new GoalDetailResponseDto();
        // Assuming setters or reflection specific to DTO if needed.
        // For verify(mapper), checking if it returns non-null is key.
        
        when(goalDetailMapper.findGoalDetail(goalId)).thenReturn(responseDto);

        // when
        GoalDetailResponseDto result = service.getGoalDetail(goalId);

        // then
        assertThat(result).isNotNull();
        verify(goalDetailMapper).findGoalDetail(goalId);
    }
}
