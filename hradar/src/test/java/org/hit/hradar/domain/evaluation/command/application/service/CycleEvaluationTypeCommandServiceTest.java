package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleEvaluationTypeSaveRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CycleEvaluationTypeCommandServiceTest {

    @InjectMocks
    private CycleEvaluationTypeCommandService service;

    @Mock
    private CycleRepository cycleRepository;

    @Mock
    private CycleEvaluationTypeRepository cycleEvaluationTypeRepository;

    @Test
    @DisplayName("회차별 평가 유형 저장 - 성공")
    void save_success() {
        // given
        Long cycleId = 1L;
        CycleEvaluationTypeSaveRequest request = new CycleEvaluationTypeSaveRequest();
        setField(request, "evalTypeIds", List.of(10L, 20L));

        Cycle cycle = mock(Cycle.class);
        when(cycleRepository.findById(cycleId)).thenReturn(Optional.of(cycle));

        when(cycleEvaluationTypeRepository.findByCycleIdAndIsDeleted(cycleId, 'N'))
                .thenReturn(Collections.emptyList());

        // when
        service.save(cycleId, request);

        // then
        verify(cycleEvaluationTypeRepository, times(2)).save(any(CycleEvaluationType.class));
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
