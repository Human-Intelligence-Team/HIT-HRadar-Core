package org.hit.hradar.domain.goal.command.application.service;

import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiProgressRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;
import org.hit.hradar.domain.goal.command.domain.aggregate.KpiProgressLog;
import org.hit.hradar.domain.goal.command.domain.repository.KpiDetailRepository;
import org.hit.hradar.domain.goal.command.infrastructure.KpiProgressLogJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KpiProgressCommandServiceTest {

    @InjectMocks
    private KpiProgressCommandService service;

    @Mock
    private KpiDetailRepository kpiDetailRepository;

    @Mock
    private KpiProgressLogJpaRepository kpiProgressLogJpaRepository;

    @Test
    @DisplayName("KPI 성과 입력 - 성공")
    void createProgress_success() {
        // given
        Long kpiId = 1L;
        Long actorId = 10L;
        CreateKpiProgressRequest request = new CreateKpiProgressRequest();
        setField(request, "logDate", LocalDate.now());
        setField(request, "logValue", BigDecimal.valueOf(10));

        Goal goal = mock(Goal.class);
        // Ensure validation constraints pass (e.g., date checks)
        when(goal.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(goal.getEndDate()).thenReturn(LocalDate.now().plusDays(1));
        // GoalValidationPolicy.validateProgressCreatable checks if goal is APPROVED or similar status usually.
        // Assuming loose validation in unit test unless Policy logic is mocked or Goal status set.
        // If validation uses static method, we might need to rely on Goal state.

        KpiDetail kpi = mock(KpiDetail.class);
        when(kpi.getGoal()).thenReturn(goal);
        
        when(kpiDetailRepository.findById(kpiId)).thenReturn(Optional.of(kpi));

        // when
        service.createProgress(kpiId, actorId, request);

        // then
        verify(kpiProgressLogJpaRepository).save(any(KpiProgressLog.class));
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
