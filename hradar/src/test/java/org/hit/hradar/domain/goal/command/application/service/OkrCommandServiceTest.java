package org.hit.hradar.domain.goal.command.application.service;

import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrKeyResultRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.hit.hradar.domain.goal.command.domain.repository.OkrKeyResultRepository;
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
class OkrCommandServiceTest {

    @InjectMocks
    private OkrCommandService service;

    @Mock
    private OkrKeyResultRepository okrKeyResultRepository;

    @Mock
    private GoalRepository goalRepository;

    @Test
    @DisplayName("OKR Key Result 생성 - 성공")
    void createKeyResult_success() {
        // given
        Long goalId = 1L;
        CreateOkrKeyResultRequest request = new CreateOkrKeyResultRequest();
        setField(request, "content", "Key Result Content");
        setField(request, "metricName", "Metric");
        setField(request, "targetValue", BigDecimal.valueOf(100));

        Goal goal = mock(Goal.class);
        when(goal.getGoalId()).thenReturn(goalId);
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));

        OkrKeyResult savedKr = mock(OkrKeyResult.class);
        when(savedKr.getKeyResultId()).thenReturn(300L);
        when(okrKeyResultRepository.save(any(OkrKeyResult.class))).thenReturn(savedKr);

        // when
        Long krId = service.createKeyResult(goalId, request);

        // then
        assertThat(krId).isEqualTo(300L);
        verify(okrKeyResultRepository).save(any(OkrKeyResult.class));
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
