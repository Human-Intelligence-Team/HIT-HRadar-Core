package org.hit.hradar.domain.grading.query.service;

import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionAdminResponseDto;
import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionResponseDto;
import org.hit.hradar.domain.grading.query.mapper.GradeObjectionMapper;
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
class GradeObjectionQueryServiceTest {

    @InjectMocks
    private GradeObjectionQueryService service;

    @Mock
    private GradeObjectionMapper gradeObjectionMapper;

    @Test
    @DisplayName("개인 등급별 이의제기 조회 - 성공")
    void getObjectionsByIndividualGrade_success() {
        // given
        Long individualGradeId = 1L;
        GradeObjectionResponseDto dto = new GradeObjectionResponseDto();
        
        when(gradeObjectionMapper.findByIndividualGradeId(individualGradeId)).thenReturn(List.of(dto));

        // when
        List<GradeObjectionResponseDto> result = service.getObjectionsByIndividualGrade(individualGradeId);

        // then
        assertThat(result).hasSize(1);
        verify(gradeObjectionMapper).findByIndividualGradeId(individualGradeId);
    }

    @Test
    @DisplayName("전체 이의제기 조회 - 성공")
    void getAllObjections_success() {
        // given
        Long companyId = 1L;
        GradeObjectionAdminResponseDto dto = new GradeObjectionAdminResponseDto();

        when(gradeObjectionMapper.findAllByCompany(companyId)).thenReturn(List.of(dto));

        // when
        List<GradeObjectionAdminResponseDto> result = service.getAllObjections(companyId);

        // then
        assertThat(result).hasSize(1);
        verify(gradeObjectionMapper).findAllByCompany(companyId);
    }
}
