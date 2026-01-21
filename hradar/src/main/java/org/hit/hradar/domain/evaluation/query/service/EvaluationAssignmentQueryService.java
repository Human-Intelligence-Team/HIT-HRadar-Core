package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentListResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentResponseDto;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationAssignmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationAssignmentQueryService {

    private final EvaluationAssignmentMapper assignmentMapper;

    //내가 평가해야할 목록 조회
    public List<EvaluationAssignmentResponseDto> getMyAssignments(Long employeeId) {
        return assignmentMapper.selectAssignmentsByEvaluator(employeeId);
    }

    //평가 배정 상태 보기
    public List<EvaluationAssignmentListResponseDto> getAssignments(
            Long cycleId,
            String evalTypeCode,
            String status,
            Long evaluatorId,
            Long evaluateeId
    ) {
        return assignmentMapper.selectAssignments(
                cycleId,
                evalTypeCode,
                status,
                evaluatorId,
                evaluateeId
        );
    }
}
