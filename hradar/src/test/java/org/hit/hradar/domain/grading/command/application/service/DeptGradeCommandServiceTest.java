package org.hit.hradar.domain.grading.command.application.service;

import org.hit.hradar.domain.grading.command.aplication.dto.request.AssignDeptGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.DeptGradeCommandService;
import org.hit.hradar.domain.grading.command.domain.aggregate.DeptGrade;
import org.hit.hradar.domain.grading.command.domain.repository.DeptGradeRepository;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
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
class DeptGradeCommandServiceTest {

    @InjectMocks
    private DeptGradeCommandService service;

    @Mock
    private DeptGradeRepository deptGradeRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private EvaluationCycleStatusProvider evaluationCycleStatusProvider;

    @Test
    @DisplayName("부서 등급 배정 - 성공")
    void assignDeptGrade_success() {
        // given
        AssignDeptGradeRequestDto request = new AssignDeptGradeRequestDto();
        setField(request, "companyId", 1L);
        setField(request, "cycleId", 202401L);
        setField(request, "departmentId", 10L);
        setField(request, "gradeId", 100L);
        
        Long employeeId = 50L;

        when(evaluationCycleStatusProvider.existsInProgressCycle(1L)).thenReturn(true);

        DeptGrade savedDeptGrade = mock(DeptGrade.class);
        when(deptGradeRepository.save(any(DeptGrade.class))).thenReturn(savedDeptGrade);

        // when
        service.assignDeptGrade(request, employeeId);

        // then
        verify(deptGradeRepository).save(any(DeptGrade.class));
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
