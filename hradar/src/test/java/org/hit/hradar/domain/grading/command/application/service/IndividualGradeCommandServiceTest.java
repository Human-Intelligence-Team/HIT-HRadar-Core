package org.hit.hradar.domain.grading.command.application.service;

import org.hit.hradar.domain.grading.command.aplication.dto.request.AssignIndividualGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.IndividualGradeCommandService;
import org.hit.hradar.domain.grading.command.domain.aggregate.IndividualGrade;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.command.domain.repository.IndividualGradeRepository;
import org.hit.hradar.domain.grading.port.EvaluationCycleStatusProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndividualGradeCommandServiceTest {

    @InjectMocks
    private IndividualGradeCommandService service;

    @Mock
    private IndividualGradeRepository individualGradeRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private EvaluationCycleStatusProvider evaluationCycleStatusProvider;

    @Test
    @DisplayName("개인 등급 등록 - 성공")
    void assignIndividualGrade_success() {
        // given
        Long compId = 1L;
        AssignIndividualGradeRequestDto request = new AssignIndividualGradeRequestDto();
        setField(request, "cycleId", 202401L);
        setField(request, "empId", 100L);
        setField(request, "gradeId", 200L);
        setField(request, "gradeReason", "Good job");

        when(evaluationCycleStatusProvider.existsInProgressCycle(compId)).thenReturn(true);

        // when
        service.assignIndividualGrade(compId, request);

        // then
        verify(individualGradeRepository).save(any(IndividualGrade.class));
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
