package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluationSubmissionServiceTest {

    @InjectMocks
    private EvaluationSubmissionService service;

    @Mock
    private EvaluationAssignmentRepository evaluationAssignmentRepository;

    @Test
    @DisplayName("평가 제출 - 성공")
    void submit_success() {
        // given
        Long assignmentId = 1L;
        EvaluationAssignment assignment = mock(EvaluationAssignment.class);
        
        when(evaluationAssignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        // when
        service.submit(assignmentId);

        // then
        verify(assignment).submit(any());
    }
}
