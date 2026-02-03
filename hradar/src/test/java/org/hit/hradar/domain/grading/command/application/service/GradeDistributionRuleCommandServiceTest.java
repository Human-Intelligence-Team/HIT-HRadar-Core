package org.hit.hradar.domain.grading.command.application.service;

import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterGradeDistributionRuleRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.GradeDistributionRuleCommandService;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.aggregate.GradeDistributionRule;
import org.hit.hradar.domain.grading.command.domain.repository.GradeDistributionRuleRepository;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeDistributionRuleCommandServiceTest {

    @InjectMocks
    private GradeDistributionRuleCommandService service;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private GradeDistributionRuleRepository ruleRepository;

    @Test
    @DisplayName("등급 배분 정책 등록 - 성공")
    void registerDistributionRule_success() {
        // given
        Long companyId = 1L;
        Long gradeId = 10L;
        RegisterGradeDistributionRuleRequestDto request = new RegisterGradeDistributionRuleRequestDto();
        setField(request, "minRatio", 10);
        setField(request, "maxRatio", 20);

        Grade grade = mock(Grade.class);
        when(grade.getCompanyId()).thenReturn(companyId);
        when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(grade));

        when(ruleRepository.existsByGradeIdAndIsDeleted(gradeId, 'N')).thenReturn(false);

        // Validation for total max ratio
        when(gradeRepository.findAllByCompanyIdAndIsDeleted(companyId, 'N')).thenReturn(Collections.emptyList());

        // when
        service.registerDistributionRule(companyId, gradeId, request);

        // then
        verify(ruleRepository).save(any(GradeDistributionRule.class));
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
