package org.hit.hradar.domain.grading.query.service;

import org.hit.hradar.domain.grading.query.dto.response.MyIndividualGradeResponseDto;
import org.hit.hradar.domain.grading.query.mapper.MyIndividualGradeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndividualGradeQueryServiceTest {

    @InjectMocks
    private IndividualGradeQueryService service;

    @Mock
    private MyIndividualGradeMapper myIndividualGradeMapper;

    @Test
    @DisplayName("내 등급 조회 - 성공")
    void getMyIndividualGrade_success() {
        // given
        Long employeeId = 1L;
        Long cycleId = 202401L;
        MyIndividualGradeResponseDto dto = mock(MyIndividualGradeResponseDto.class);
        
        when(myIndividualGradeMapper.findMyIndividualGrade(employeeId, cycleId)).thenReturn(dto);

        // when
        MyIndividualGradeResponseDto result = service.getMyIndividualGrade(employeeId, cycleId);

        // then
        assertThat(result).isEqualTo(dto);
        verify(myIndividualGradeMapper).findMyIndividualGrade(employeeId, cycleId);
    }

    @Test
    @DisplayName("내 등급 조회 - 미부여시 null 반환")
    void getMyIndividualGrade_notFound() {
        // given
        Long employeeId = 1L;
        Long cycleId = 202401L;
        
        when(myIndividualGradeMapper.findMyIndividualGrade(employeeId, cycleId)).thenReturn(null);

        // when
        MyIndividualGradeResponseDto result = service.getMyIndividualGrade(employeeId, cycleId);

        // then
        assertThat(result.getGradeName()).isNull();
    }
}
