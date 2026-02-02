package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentResponse;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationAssignmentAdminMapper;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationAssignmentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluationAssignmentQueryServiceTest {

    @InjectMocks
    private EvaluationAssignmentQueryService service;

    @Mock
    private EvaluationAssignmentMapper assignmentMapper;

    @Mock
    private EvaluationAssignmentAdminMapper assignmentAdminMapper;

    @Test
    @DisplayName("평가자별 할당 조회 - 성공")
    void getAssignmentsByEvaluator_success() {
        // given
        Long evaluatorId = 100L;
        EvaluationAssignmentResponse dto = new EvaluationAssignmentResponse();
        // Assuming fields set via reflection/setter if needed
        
        when(assignmentMapper.findByEvaluatorId(evaluatorId)).thenReturn(List.of(dto));

        // when
        List<EvaluationAssignmentResponse> result = service.getAssignmentsByEvaluator(evaluatorId);

        // then
        assertThat(result).hasSize(1);
        verify(assignmentMapper).findByEvaluatorId(evaluatorId);
    }
}
