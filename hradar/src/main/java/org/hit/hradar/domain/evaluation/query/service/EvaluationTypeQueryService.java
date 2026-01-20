package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationTypeResponseDto;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationTypeQueryService {

    private final EvaluationTypeMapper evaluationTypeMapper;

    public List<EvaluationTypeResponseDto> getEvaluationTypes(Long cycleId){
        return evaluationTypeMapper.selectEvaluationTypesByCycle(cycleId);
    }
}
