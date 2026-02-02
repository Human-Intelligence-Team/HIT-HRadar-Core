package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationTypeResponse;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationTypeMapper;
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
class EvaluationTypeQueryServiceTest {

    @InjectMocks
    private EvaluationTypeQueryService service;

    @Mock
    private EvaluationTypeMapper evaluationTypeMapper;

    @Test
    @DisplayName("평가 유형 목록 조회 - 성공")
    void getEvaluationTypes_success() {
        // given
        Long companyId = 1L;
        EvaluationTypeResponse dto = mock(EvaluationTypeResponse.class);
        // set fields
        
        when(evaluationTypeMapper.selectEvaluationTypesByCompanyId(companyId)).thenReturn(List.of(dto));

        // when
        List<EvaluationTypeResponse> result = service.getEvaluationTypes(companyId);

        // then
        assertThat(result).hasSize(1);
        verify(evaluationTypeMapper).selectEvaluationTypesByCompanyId(companyId);
    }
}
