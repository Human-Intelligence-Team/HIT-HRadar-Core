package org.hit.hradar.domain.grading.command.application.service;

import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.CompanyGradeCommandService;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.port.EvaluationCycleStatusProvider;
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
class CompanyGradeCommandServiceTest {

    @InjectMocks
    private CompanyGradeCommandService service;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private EvaluationCycleStatusProvider evaluationCycleStatusProvider;

    @Test
    @DisplayName("회사 등급 등록 - 성공")
    void registerCompanyGrade_success() {
        // given
        Long compId = 1L;
        RegisterCompanyGradeRequestDto request = new RegisterCompanyGradeRequestDto();
        setField(request, "gradeName", "S-Grade");
        setField(request, "gradeOrder", 1);
        
        when(gradeRepository.existsByCompanyIdAndGradeNameAndIsDeleted(eq(compId), eq("S-Grade"), eq('N'))).thenReturn(false);
        when(gradeRepository.existsByCompanyIdAndGradeOrderAndIsDeleted(eq(compId), eq(1), eq('N'))).thenReturn(false);

        // Mock save to simulate ID generation
        when(gradeRepository.save(any(Grade.class))).thenAnswer(invocation -> {
            Grade g = invocation.getArgument(0);
            setField(g, "gradeId", 100L);
            return g;
        });

        // when
        Long gradeId = service.registerCompanyGrade(compId, request);

        // then
        assertThat(gradeId).isEqualTo(100L);
        verify(gradeRepository).save(any(Grade.class));
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
