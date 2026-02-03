package org.hit.hradar.domain.goal.query.service;

import org.hit.hradar.domain.goal.query.dto.response.KpiListResponseDto;
import org.hit.hradar.domain.goal.query.mapper.KpiListMapper;
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
class KpiListQueryServiceTest {

    @InjectMocks
    private KpiListQueryService service;

    @Mock
    private KpiListMapper goalKpiListMapper;

    @Test
    @DisplayName("KPI 목록 조회 - 성공")
    void getKpis_success() {
        // given
        Long goalId = 1L;
        KpiListResponseDto dto = new KpiListResponseDto();
        
        when(goalKpiListMapper.findKpisByGoalId(goalId)).thenReturn(List.of(dto));

        // when
        List<KpiListResponseDto> result = service.getKpis(goalId);

        // then
        assertThat(result).hasSize(1);
        verify(goalKpiListMapper).findKpisByGoalId(goalId);
    }
}
