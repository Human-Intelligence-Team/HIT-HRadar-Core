package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentAdminResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentResponse;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationAssignmentAdminMapper;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationAssignmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationAssignmentQueryService {

    private final EvaluationAssignmentMapper assignmentMapper;
    private final EvaluationAssignmentAdminMapper assignmentAdminMapper;

    //평가자 기준 조회
    public List<EvaluationAssignmentResponse> getAssignmentsByEvaluator(Long evaluatorId) {
        return assignmentMapper.findByEvaluatorId(evaluatorId);
    }

    //전체 조회
    public List<EvaluationAssignmentAdminResponse> getAssignments(Long cycleEvalTypeId) {
        return assignmentAdminMapper.findAssignmentsByCycleEvalType(cycleEvalTypeId);
    }
}
