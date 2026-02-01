package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.JobSatisfactionChartResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.JobSatisfactionRow;
import org.hit.hradar.domain.evaluation.query.mapper.JobSatisfactionChartMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 직무만족도 차트 조회 서비스
 *
 * - DB에서 문항별 0~100 점수를 가져와 labels/barValues로 매핑
 * - gauge.average: barValues의 평균(소수 1자리)
 * - gauge.percentage: average를 반올림한 정수(0~100)
 */
@Service
@RequiredArgsConstructor
public class JobSatisfactionChartQueryService {

    private final JobSatisfactionChartMapper jobSatisfactionChartMapper;

    public JobSatisfactionChartResponse getMyJobSatisfactionChart(Long evaluateeId) {
        List<JobSatisfactionRow> rows = jobSatisfactionChartMapper.selectMyJobSatisfactionBars(evaluateeId);

        List<String> labels = rows.stream()
                .map(JobSatisfactionRow::getLabel)
                .toList();

        List<Double> barValues = rows.stream()
                .map(r -> r.getValue() == null ? 0.0 : r.getValue())
                .toList();

        // 평균(소수 1자리) 계산: rows가 없으면 0.0
        double avg = 0.0;
        if (!barValues.isEmpty()) {
            double sum = 0.0;
            for (Double v : barValues) sum += (v == null ? 0.0 : v);
            avg = sum / barValues.size();
            avg = round1(avg);
        }

        // 퍼센트(정수): 반올림. (원하면 floor/ceil로 바꿔도 됨)
        int percentage = (int) Math.round(avg);

        return JobSatisfactionChartResponse.builder()
                .labels(labels)
                .barValues(barValues)
                .gauge(JobSatisfactionChartResponse.Gauge.builder()
                        .average(avg)
                        .percentage(percentage)
                        .build())
                .build();
    }

    /**
     * 소수 1자리 반올림 유틸 (DB에서 이미 ROUND(...,1)로 내려오긴 하지만
     * 혹시 추가 계산/후처리가 들어갈 때를 대비해서 서비스에서도 보정)
     */
    private double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
