package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseItemRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseUpsertRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.*;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationQuestionRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationResponseRepository;
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
class EvaluationResponseCommandServiceTest {

    @InjectMocks
    private EvaluationResponseCommandService service;

    @Mock
    private EvaluationResponseRepository evaluationResponseRepository;

    @Mock
    private EvaluationAssignmentRepository evaluationAssignmentRepository;

    @Mock
    private EvaluationQuestionRepository evaluationQuestionRepository;

    @Test
    @DisplayName("평가 임시저장 - 성공")
    void saveDraft_success() {
        // given
        Long assignmentId = 1L;
        EvaluationResponseUpsertRequest request = new EvaluationResponseUpsertRequest();
        setField(request, "assignmentId", assignmentId);
        
        EvaluationResponseItemRequest item = new EvaluationResponseItemRequest();
        setField(item, "questionId", 100L);
        setField(item, "responseType", "RATING");
        setField(item, "score", 5);
        setField(request, "responses", Collections.singletonList(item));

        EvaluationAssignment assignment = mock(EvaluationAssignment.class);
        when(evaluationAssignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        EvaluationQuestion question = mock(EvaluationQuestion.class);
        when(question.getQuestionId()).thenReturn(100L);
        when(evaluationQuestionRepository.findById(100L)).thenReturn(Optional.of(question));

        // when
        service.saveDraft(request);

        // then
        verify(evaluationResponseRepository).save(any(EvaluationResponse.class));
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
