package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleEvaluationTypeResponse;
import org.hit.hradar.domain.evaluation.query.mapper.CycleEvaluationTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CycleEvaluationTypeQueryService {

    private final CycleEvaluationTypeMapper cycleEvaluationTypeMapper;

    public List<CycleEvaluationTypeResponse> getEvaluationTypes(Long cycleId) {
        return cycleEvaluationTypeMapper.selectEvaluationTypesByCycleId(cycleId);
    }
}
