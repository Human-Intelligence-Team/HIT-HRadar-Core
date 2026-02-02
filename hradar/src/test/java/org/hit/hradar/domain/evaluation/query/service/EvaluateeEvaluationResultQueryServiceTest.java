package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.EvaluateeEvaluationResultResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluateeResponseRow;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluateeEvaluationResultMapper;
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
class EvaluateeEvaluationResultQueryServiceTest {

    @InjectMocks
    private EvaluateeEvaluationResultQueryService service;

    @Mock
    private EvaluateeEvaluationResultMapper mapper;

    @Test
    @DisplayName("피평가자 평가 결과 조회 - 성공")
    void getEvaluateeResult_success() {
        // given
        Long evaluateeId = 1L;
        Long cycleId = 10L;
        Long evalTypeId = 20L;
        
        EvaluateeResponseRow row = new EvaluateeResponseRow();
        setField(row, "questionId", 100L);
        setField(row, "score", 5);
        setField(row, "cycleId", cycleId);
        setField(row, "evalTypeId", evalTypeId);

        when(mapper.selectEvaluateeResponses(evaluateeId, cycleId, evalTypeId))
                .thenReturn(List.of(row));

        // when
        EvaluateeEvaluationResultResponse result = service.getEvaluateeResult(evaluateeId, cycleId, evalTypeId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuestions()).hasSize(1);
        verify(mapper).selectEvaluateeResponses(evaluateeId, cycleId, evalTypeId);
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
