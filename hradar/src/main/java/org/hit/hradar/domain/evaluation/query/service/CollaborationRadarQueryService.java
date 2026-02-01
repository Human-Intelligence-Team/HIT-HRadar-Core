package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.CollaborationRadarResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.CollaborationRadarRow;
import org.hit.hradar.domain.evaluation.query.mapper.CollaborationRadarMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollaborationRadarQueryService {
    private final CollaborationRadarMapper mapper;

    public CollaborationRadarResponse getMyCollaborationRadar(Long evaluateeId) {

        List<CollaborationRadarRow> rows = mapper.selectMyCollaborationRadar(evaluateeId);

        // 레이더 축 라벨(문항 내용)
        List<String> labels = rows.stream()
                .map(CollaborationRadarRow::getLabel)
                .collect(Collectors.toList());

        // 레이더 값(0~100)
        List<Double> values = rows.stream()
                .map(r -> r.getValue() == null ? 0.0 : r.getValue())
                .collect(Collectors.toList());

        // 레이더 max는 100 고정(정규화했으므로)
        return new CollaborationRadarResponse(labels, values, 100);
    }
}
