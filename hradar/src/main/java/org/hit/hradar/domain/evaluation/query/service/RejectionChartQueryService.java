package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.RejectionCountChartResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.MonthlyRejectionCountRow;
import org.hit.hradar.domain.evaluation.query.mapper.RejectionChartMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RejectionChartQueryService {

    private final RejectionChartMapper mapper;

    public RejectionCountChartResponse getMonthlyRejectionChart(
            Long empId,
            String startYm,
            String endYm
    ) {
        List<MonthlyRejectionCountRow> rows =
                mapper.selectMonthlyRejectionCount(empId, startYm, endYm);

        List<String> labels = rows.stream()
                .map(MonthlyRejectionCountRow::getMonth)
                .toList();

        List<Long> values = rows.stream()
                .map(MonthlyRejectionCountRow::getCount)
                .toList();

        return RejectionCountChartResponse.builder()
                .labels(labels)
                .values(values)
                .build();
    }
}
