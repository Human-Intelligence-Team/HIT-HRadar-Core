package org.hit.hradar.domain.goal.command.application.service;

import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.hit.hradar.domain.goal.command.domain.repository.KpiDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KpiCommandServiceTest {

    @InjectMocks
    private KpiCommandService service;

    @Mock
    private KpiDetailRepository kpiDetailRepository;

    @Mock
    private GoalRepository goalRepository;

    @Test
    @DisplayName("KPI 생성 - 성공")
    void createKpi_success() {
        // given
        Long goalId = 1L;
        CreateKpiRequest request = new CreateKpiRequest();
        setField(request, "metricName", "New Metric");
        setField(request, "startValue", BigDecimal.ZERO);
        setField(request, "targetValue", BigDecimal.valueOf(100));

        Goal goal = mock(Goal.class);
        when(goal.getGoalId()).thenReturn(goalId);
        
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));

        KpiDetail savedKpi = mock(KpiDetail.class);
        when(savedKpi.getKpiId()).thenReturn(200L);
        when(kpiDetailRepository.save(any(KpiDetail.class))).thenReturn(savedKpi);

        // when
        Long kpiId = service.createKpi(goalId, request);

        // then
        assertThat(kpiId).isEqualTo(200L);
        verify(kpiDetailRepository).save(any(KpiDetail.class));
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
