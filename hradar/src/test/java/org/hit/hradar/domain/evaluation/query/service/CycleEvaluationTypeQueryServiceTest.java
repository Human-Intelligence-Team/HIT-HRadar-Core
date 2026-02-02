package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.CycleEvaluationTypeResponse;
import org.hit.hradar.domain.evaluation.query.mapper.CycleEvaluationTypeMapper;
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
class CycleEvaluationTypeQueryServiceTest {

    @InjectMocks
    private CycleEvaluationTypeQueryService service;

    @Mock
    private CycleEvaluationTypeMapper cycleEvaluationTypeMapper;

    @Test
    @DisplayName("회차별 평가 유형 조회 - 성공")
    void getEvaluationTypes_success() {
        // given
        Long cycleId = 1L;
        CycleEvaluationTypeResponse dto = new CycleEvaluationTypeResponse(10L, "Type");
        // Assuming fields set if necessary
        
        when(cycleEvaluationTypeMapper.selectEvaluationTypesByCycleId(cycleId)).thenReturn(List.of(dto));

        // when
        List<CycleEvaluationTypeResponse> result = service.getEvaluationTypes(cycleId);

        // then
        assertThat(result).hasSize(1);
        verify(cycleEvaluationTypeMapper).selectEvaluationTypesByCycleId(cycleId);
    }
}
