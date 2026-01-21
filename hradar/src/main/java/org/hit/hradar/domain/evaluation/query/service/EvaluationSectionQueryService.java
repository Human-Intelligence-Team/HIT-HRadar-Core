package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationSectionResponseDto;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationSectionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationSectionQueryService {

    private final EvaluationSectionMapper sectionMapper;

    public List<EvaluationSectionResponseDto> getSectionsByEvalType(Long evalTypeId) {
        return sectionMapper.selectSectionsByEvalTypeId(evalTypeId);
    }
}
