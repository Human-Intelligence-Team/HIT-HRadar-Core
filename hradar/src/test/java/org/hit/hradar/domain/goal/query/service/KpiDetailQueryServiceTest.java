package org.hit.hradar.domain.goal.query.service;

import org.hit.hradar.domain.goal.query.dto.response.KpiDetailResponseDto;
import org.hit.hradar.domain.goal.query.mapper.KpiDetailMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KpiDetailQueryServiceTest {

    @InjectMocks
    private KpiDetailQueryService service;

    @Mock
    private KpiDetailMapper kpiDetailMapper;

    @Test
    @DisplayName("KPI 상세 조회 - 성공")
    void getKpiDetail_success() {
        // given
        Long kpiId = 1L;
        KpiDetailResponseDto response = new KpiDetailResponseDto();
        
        when(kpiDetailMapper.findKpiSummary(kpiId)).thenReturn(response);
        when(kpiDetailMapper.findKpiLogs(kpiId)).thenReturn(Collections.emptyList());

        // when
        KpiDetailResponseDto result = service.getKpiDetail(kpiId);

        // then
        assertThat(result).isNotNull();
        verify(kpiDetailMapper).findKpiSummary(kpiId);
        verify(kpiDetailMapper).findKpiLogs(kpiId);
    }
}
