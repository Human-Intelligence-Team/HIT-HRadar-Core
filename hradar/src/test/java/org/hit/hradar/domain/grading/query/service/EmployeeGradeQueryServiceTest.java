package org.hit.hradar.domain.grading.query.service;

import org.hit.hradar.domain.grading.query.dto.response.EmployeeGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.mapper.EmployeeGradeMapper;
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
class EmployeeGradeQueryServiceTest {

    @InjectMocks
    private EmployeeGradeQueryService service;

    @Mock
    private EmployeeGradeMapper employeeGradeMapper;

    @Test
    @DisplayName("구성원 등급 현황 조회 - 성공")
    void getEmployeeGradeStatusList_success() {
        // given
        Long companyId = 1L;
        Long deptId = 10L;
        Long cycleId = 202401L;

        EmployeeGradeStatusResponseDto dto = new EmployeeGradeStatusResponseDto();
        
        when(employeeGradeMapper.findEmployeeGradeStatusList(companyId, deptId, cycleId)).thenReturn(List.of(dto));

        // when
        List<EmployeeGradeStatusResponseDto> result = service.getEmployeeGradeStatusList(companyId, deptId, cycleId);

        // then
        assertThat(result).hasSize(1);
        verify(employeeGradeMapper).findEmployeeGradeStatusList(companyId, deptId, cycleId);
    }
}
