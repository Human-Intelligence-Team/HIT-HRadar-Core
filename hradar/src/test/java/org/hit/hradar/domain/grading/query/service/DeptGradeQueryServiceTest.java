package org.hit.hradar.domain.grading.query.service;

import org.hit.hradar.domain.grading.query.dto.response.DeptGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.mapper.DeptGradeMapper;
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
class DeptGradeQueryServiceTest {

    @InjectMocks
    private DeptGradeQueryService service;

    @Mock
    private DeptGradeMapper deptGradeMapper;

    @Test
    @DisplayName("부서 등급 현황 조회 - 성공")
    void getDeptGradeStatusList_success() {
        // given
        Long companyId = 1L;
        Long cycleId = 202401L;
        DeptGradeStatusResponseDto dto = new DeptGradeStatusResponseDto();
        // set fields if necessary
        
        when(deptGradeMapper.findDeptGradeStatusList(companyId, cycleId)).thenReturn(List.of(dto));

        // when
        List<DeptGradeStatusResponseDto> result = service.getDeptGradeStatusList(companyId, cycleId);

        // then
        assertThat(result).hasSize(1);
        verify(deptGradeMapper).findDeptGradeStatusList(companyId, cycleId);
    }
}
