package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationAssignmentCreateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluationAssignmentCommandServiceTest {

    @InjectMocks
    private EvaluationAssignmentCommandService service;

    @Mock
    private EvaluationAssignmentRepository assignmentRepository;

    @Mock
    private CycleEvaluationTypeRepository cycleEvaluationTypeRepository;

    @Test
    @DisplayName("평가 배정 생성 - 성공")
    void createAssignments_success() {
        // given
        Long cycleEvalTypeId = 1L;
        EvaluationAssignmentCreateRequest request = new EvaluationAssignmentCreateRequest();
        setField(request, "evaluatorId", 100L);
        setField(request, "evaluateeIds", List.of(200L));

        CycleEvaluationType cycleEvalType = mock(CycleEvaluationType.class);
        when(cycleEvaluationTypeRepository.findById(cycleEvalTypeId)).thenReturn(Optional.of(cycleEvalType));

        when(assignmentRepository.existsByCycleEvaluationTypeAndEvaluatorIdAndEvaluateeIdAndIsDeleted(
                any(), eq(100L), eq(200L), eq('N'))).thenReturn(false);

        // when
        service.createAssignments(cycleEvalTypeId, request);

        // then
        verify(assignmentRepository).save(any(EvaluationAssignment.class));
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
