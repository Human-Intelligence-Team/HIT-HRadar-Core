package org.hit.hradar.domain.goal.command.application.service;

import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.hit.hradar.domain.evaluation.command.infrastructure.RejectionEventJpaRepository;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateGoalRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalScope;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalCommandServiceTest {

    @InjectMocks
    private GoalCommandService service;

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private RejectionEventJpaRepository rejectionEventJpaRepository;

    @Test
    @DisplayName("Root 목표 생성 - 성공")
    void createRootGoal_success() {
        // given
        CreateGoalRequest request = new CreateGoalRequest();
        // Set fields via reflection or stubbing if no public setters
        setField(request, "goalScope", GoalScope.TEAM);
        setField(request, "goalType", GoalType.KPI);
        setField(request, "title", "2024 Team Goal");
        setField(request, "departmentId", 10L);

        Long actorId = 1L;
        Goal savedGoal = mock(Goal.class);
        when(savedGoal.getGoalId()).thenReturn(100L);

        when(goalRepository.save(any(Goal.class))).thenReturn(savedGoal);

        // when
        Long goalId = service.createRootGoal(request, actorId);

        // then
        assertThat(goalId).isEqualTo(100L);
        verify(goalRepository).save(any(Goal.class));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
