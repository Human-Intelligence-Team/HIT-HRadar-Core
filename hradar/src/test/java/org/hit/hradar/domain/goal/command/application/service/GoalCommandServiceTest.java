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
import org.hit.hradar.domain.goal.command.application.dto.request.UpdateGoalRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalApproveStatus;
import org.hit.hradar.global.exception.BusinessException;
import java.time.LocalDate;
import java.util.Optional;
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

    @Test
    @DisplayName("Goal 수정 - DRAFT 상태 성공")
    void updateGoal_draft_success() {
        // given
        Long goalId = 1L;
        Long actorId = 1L;
        UpdateGoalRequest request = new UpdateGoalRequest();
        setField(request, "title", "Updated Title");
        setField(request, "startDate", LocalDate.now());
        setField(request, "endDate", LocalDate.now().plusDays(10));
        setField(request, "scope", GoalScope.TEAM);

        Goal goal = mock(Goal.class);
        when(goal.getApproveStatus()).thenReturn(GoalApproveStatus.DRAFT);
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));

        // when
        service.updateGoal(goalId, request, actorId);

        // then
        verify(goal).updateDraft(any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("Goal 수정 - SUBMITTED 상태 성공")
    void updateGoal_submitted_success() {
        // given
        Long goalId = 1L;
        Long actorId = 1L;
        UpdateGoalRequest request = new UpdateGoalRequest();
        setField(request, "title", "Updated Title");
        setField(request, "startDate", LocalDate.now());
        setField(request, "endDate", LocalDate.now().plusDays(10));
        setField(request, "scope", GoalScope.TEAM);

        Goal goal = mock(Goal.class);
        when(goal.getApproveStatus()).thenReturn(GoalApproveStatus.SUBMITTED);
        // validateRequiredFields mock setup if needed, or lenient
        when(goal.getTitle()).thenReturn("Old Title"); 
        when(goal.getStartDate()).thenReturn(LocalDate.now());
        when(goal.getEndDate()).thenReturn(LocalDate.now().plusDays(10));
        when(goal.getScope()).thenReturn(GoalScope.TEAM);
        when(goal.getType()).thenReturn(GoalType.KPI);
        
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));
        
        // when
        service.updateGoal(goalId, request, actorId);

        // then
        verify(goal).updateAfterSubmit(any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("Goal 수정 - APPROVED 상태 실패")
    void updateGoal_approved_fail() {
        // given
        Long goalId = 1L;
        Long actorId = 1L;
        UpdateGoalRequest request = new UpdateGoalRequest();
        
        Goal goal = mock(Goal.class);
        when(goal.getApproveStatus()).thenReturn(GoalApproveStatus.APPROVED);
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> {
            service.updateGoal(goalId, request, actorId);
        });
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
