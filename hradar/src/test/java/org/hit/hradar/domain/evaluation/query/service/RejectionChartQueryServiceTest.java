package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.RejectionCountChartResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.MonthlyRejectionCountRow;
import org.hit.hradar.domain.evaluation.query.mapper.RejectionChartMapper;
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
class RejectionChartQueryServiceTest {

    @InjectMocks
    private RejectionChartQueryService service;

    @Mock
    private RejectionChartMapper mapper;

    @Test
    @DisplayName("반려 횟수 차트 조회 - 성공")
    void getMonthlyRejectionChart_success() {
        // given
        Long empId = 1L;
        String startYm = "202401";
        String endYm = "202403";
        
        MonthlyRejectionCountRow row = new MonthlyRejectionCountRow();
        setField(row, "month", "2024-01");
        setField(row, "count", 2L);

        when(mapper.selectMonthlyRejectionCount(empId, startYm, endYm)).thenReturn(List.of(row));

        // when
        RejectionCountChartResponse result = service.getMonthlyRejectionChart(empId, startYm, endYm);

        // then
        assertThat(result.getLabels()).contains("2024-01");
        assertThat(result.getValues()).contains(2L);
        verify(mapper).selectMonthlyRejectionCount(empId, startYm, endYm);
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
