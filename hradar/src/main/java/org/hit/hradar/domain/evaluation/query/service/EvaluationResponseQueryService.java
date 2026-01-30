package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationResponseQueryDto;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationResponseQueryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationResponseQueryService {

    private final EvaluationResponseQueryMapper mapper;

    public List<EvaluationResponseQueryDto> getResponses(Long assignmentId) {
        return mapper.selectResponsesByAssignmentId(assignmentId);
    }
}