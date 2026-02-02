package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationSectionResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluationQuestionRow;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluationSectionRow;
import org.hit.hradar.domain.evaluation.query.mapper.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluationSheetQueryServiceTest {

    @InjectMocks
    private EvaluationSheetQueryService service;

    @Mock
    private CycleEvaluationTypeMapper cycleEvaluationTypeMapper;

    @Mock
    private EvaluationSectionMapper evaluationSectionMapper;

    @Mock
    private EvaluationQuestionMapper evaluationQuestionMapper;

    @Mock
    private ObjectiveOptionMapper objectiveOptionMapper;

    @Test
    @DisplayName("평가 시트 조회 - 성공")
    void getEvaluationSheet_success() {
        // given
        Long cycleId = 1L;
        Long evalTypeId = 2L;
        Long cycleEvalTypeId = 100L;

        when(cycleEvaluationTypeMapper.findCycleEvalTypeId(cycleId, evalTypeId)).thenReturn(cycleEvalTypeId);

        EvaluationSectionRow sectionRow = new EvaluationSectionRow();
        setField(sectionRow, "sectionId", 10L);
        when(evaluationSectionMapper.findByCycleEvalTypeId(cycleEvalTypeId)).thenReturn(List.of(sectionRow));

        EvaluationQuestionRow questionRow = new EvaluationQuestionRow();
        setField(questionRow, "questionId", 20L);
        setField(questionRow, "sectionId", 10L);
        when(evaluationQuestionMapper.findBySectionIds(anyList())).thenReturn(List.of(questionRow));

        // Assuming no options for simplicity or mock if needed
        when(objectiveOptionMapper.findByQuestionIds(anyList())).thenReturn(Collections.emptyList());

        // when
        List<EvaluationSectionResponse> result = service.getEvaluationSheet(cycleId, evalTypeId);

        // then
        assertThat(result).hasSize(1);
        verify(cycleEvaluationTypeMapper).findCycleEvalTypeId(cycleId, evalTypeId);
        verify(evaluationSectionMapper).findByCycleEvalTypeId(cycleEvalTypeId);
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
