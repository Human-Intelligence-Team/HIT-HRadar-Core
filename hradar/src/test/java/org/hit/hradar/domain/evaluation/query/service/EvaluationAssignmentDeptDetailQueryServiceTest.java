package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.DepartmentEvaluationAssignmentDetailResponse;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationAssignmentDeptDetailMapper;
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
class EvaluationAssignmentDeptDetailQueryServiceTest {

    @InjectMocks
    private EvaluationAssignmentDeptDetailQueryService service;

    @Mock
    private EvaluationAssignmentDeptDetailMapper mapper;

    @Test
    @DisplayName("부서별 평가 배정 상세 조회 - 성공")
    void getDeptAssignmentDetails_success() {
        // given
        Long deptId = 10L;
        DepartmentEvaluationAssignmentDetailResponse dto = new DepartmentEvaluationAssignmentDetailResponse();
        // set fields if needed
        
        when(mapper.selectDeptAssignmentDetails(deptId)).thenReturn(List.of(dto));

        // when
        List<DepartmentEvaluationAssignmentDetailResponse> result = service.getDeptAssignmentDetails(deptId);

        // then
        assertThat(result).hasSize(1);
        verify(mapper).selectDeptAssignmentDetails(deptId);
    }
}
