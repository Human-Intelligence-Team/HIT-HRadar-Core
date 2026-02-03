package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionCreateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationSection;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationSectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluationSectionCommandServiceTest {

    @InjectMocks
    private EvaluationSectionCommandService service;

    @Mock
    private EvaluationSectionRepository evaluationSectionRepository;

    @Mock
    private CycleEvaluationTypeRepository cycleEvaluationTypeRepository;

    @Test
    @DisplayName("섹션 생성 - 성공")
    void create_success() {
        // given
        Long cycleEvalTypeId = 1L;
        EvaluationSectionCreateRequest request = new EvaluationSectionCreateRequest();
        setField(request, "sectionTitle", "Section 1");
        setField(request, "sectionOrder", 1);

        CycleEvaluationType cycleEvalType = mock(CycleEvaluationType.class);
        when(cycleEvaluationTypeRepository.findById(cycleEvalTypeId)).thenReturn(Optional.of(cycleEvalType));

        EvaluationSection savedSection = mock(EvaluationSection.class);
        when(savedSection.getSectionId()).thenReturn(100L);
        when(evaluationSectionRepository.save(any(EvaluationSection.class))).thenReturn(savedSection);

        // when
        Long sectionId = service.create(cycleEvalTypeId, request);

        // then
        assertThat(sectionId).isEqualTo(100L);
        verify(evaluationSectionRepository).save(any(EvaluationSection.class));
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
