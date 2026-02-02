package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationResponseQueryDto;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationResponseQueryMapper;
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
class EvaluationResponseQueryServiceTest {

    @InjectMocks
    private EvaluationResponseQueryService service;

    @Mock
    private EvaluationResponseQueryMapper mapper;

    @Test
    @DisplayName("평가 응답 조회 - 성공")
    void getResponses_success() {
        // given
        Long assignmentId = 1L;
        EvaluationResponseQueryDto dto = new EvaluationResponseQueryDto();
        
        when(mapper.selectResponsesByAssignmentId(assignmentId)).thenReturn(List.of(dto));

        // when
        List<EvaluationResponseQueryDto> result = service.getResponses(assignmentId);

        // then
        assertThat(result).hasSize(1);
        verify(mapper).selectResponsesByAssignmentId(assignmentId);
    }
}
