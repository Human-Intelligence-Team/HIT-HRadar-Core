package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.CycleListResponseDto;
import org.hit.hradar.domain.evaluation.query.mapper.CycleMapper;
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
class CycleQueryServiceTest {

    @InjectMocks
    private CycleQueryService service;

    @Mock
    private CycleMapper cycleMapper;

    @Test
    @DisplayName("평가 회차 목록 조회 - 성공")
    void getCycleList_success() {
        // given
        CycleListResponseDto dto = mock(CycleListResponseDto.class);
        // Assuming fields set optionally
        
        when(cycleMapper.selectCycleList()).thenReturn(List.of(dto));

        // when
        List<CycleListResponseDto> result = service.getCycleList();

        // then
        assertThat(result).hasSize(1);
        verify(cycleMapper).selectCycleList();
    }
}
