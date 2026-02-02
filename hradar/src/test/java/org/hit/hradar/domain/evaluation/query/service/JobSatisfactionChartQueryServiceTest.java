package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.JobSatisfactionChartResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.JobSatisfactionRow;
import org.hit.hradar.domain.evaluation.query.mapper.JobSatisfactionChartMapper;
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
class JobSatisfactionChartQueryServiceTest {

    @InjectMocks
    private JobSatisfactionChartQueryService service;

    @Mock
    private JobSatisfactionChartMapper jobSatisfactionChartMapper;

    @Test
    @DisplayName("직무만족도 차트 조회 - 성공")
    void getMyJobSatisfactionChart_success() {
        // given
        Long evaluateeId = 1L;
        JobSatisfactionRow row = new JobSatisfactionRow();
        setField(row, "label", "Challenge");
        setField(row, "value", 90.0);

        when(jobSatisfactionChartMapper.selectMyJobSatisfactionBars(evaluateeId)).thenReturn(List.of(row));

        // when
        JobSatisfactionChartResponse result = service.getMyJobSatisfactionChart(evaluateeId);

        // then
        assertThat(result.getLabels()).contains("Challenge");
        assertThat(result.getBarValues()).contains(90.0);
        assertThat(result.getGauge().getAverage()).isEqualTo(90.0);
        verify(jobSatisfactionChartMapper).selectMyJobSatisfactionBars(evaluateeId);
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
