package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionCreateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.*;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationQuestionRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationSectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluationQuestionCommandServiceTest {

    @InjectMocks
    private EvaluationQuestionCommandService service;

    @Mock
    private EvaluationQuestionRepository questionRepository;

    @Mock
    private EvaluationSectionRepository sectionRepository;

    @Mock
    private CycleRepository cycleRepository;

    @Test
    @DisplayName("질문 생성 - 성공")
    void create_success() {
        // given
        EvaluationQuestionCreateRequest request = new EvaluationQuestionCreateRequest();
        setField(request, "sectionId", 10L);
        setField(request, "questionType", QuestionType.RATING);
        setField(request, "questionContent", "Content");
        setField(request, "ratingScale", 5);

        EvaluationSection section = mock(EvaluationSection.class);
        CycleEvaluationType cycleEvalType = mock(CycleEvaluationType.class);
        when(section.getCycleEvaluationType()).thenReturn(cycleEvalType);
        when(cycleEvalType.getCycleId()).thenReturn(1L);

        when(sectionRepository.findById(10L)).thenReturn(Optional.of(section));

        Cycle cycle = mock(Cycle.class);
        when(cycle.getStatus()).thenReturn(CycleStatus.DRAFT);
        when(cycleRepository.findById(1L)).thenReturn(Optional.of(cycle));

        // when
        service.create(request);

        // then
        verify(questionRepository).save(any(EvaluationQuestion.class));
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
