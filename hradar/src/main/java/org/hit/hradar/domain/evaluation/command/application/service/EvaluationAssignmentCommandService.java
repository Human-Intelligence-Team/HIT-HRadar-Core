package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationAssignmentCreateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationAssignmentCommandService {

    private final EvaluationAssignmentRepository assignmentRepository;
    private final CycleEvaluationTypeRepository  cycleEvaluationTypeRepository;

    /*평가 배정 생성*/
    @Transactional
    public List<Long> createAssignments(
            Long cycleEvalTypeId,
            EvaluationAssignmentCreateRequest request
    ){
        //회차별 평가 타입 조회
        CycleEvaluationType cycleEvalType =
                cycleEvaluationTypeRepository.findById(cycleEvalTypeId)
                        .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_EVAL_TYPE_NOT_FOUND));

        Long evaluatorId = request.getEvaluatorId();
        List<Long> assignmentIds = new ArrayList<>();

        for (Long evaluateeId : request.getEvaluateeIds()) {
            //자신 평가 방지
            if (evaluatorId.equals(evaluateeId)) {
                throw new BusinessException(
                        EvaluationErrorCode.EVALUATION_ASSIGNMENT_SELF_NOT_ALLOWED
                );
            }

            //중복 배정 방지
            boolean exists =
                    assignmentRepository.existsByCycleEvaluationTypeAndEvaluatorIdAndEvaluateeIdAndIsDeleted(
                            cycleEvalType,
                            evaluatorId,
                            evaluateeId,
                            'N'
                    );
            if (exists) {
                throw new BusinessException(
                        EvaluationErrorCode.EVALUATION_ASSIGNMENT_ALREADY_EXISTS
                );
            }

            //저장
            EvaluationAssignment assignment =
                    EvaluationAssignment.builder()
                            .cycleEvaluationType(cycleEvalType)
                            .evaluatorId(evaluatorId)
                            .evaluateeId(evaluateeId)
                            .build();

            assignmentRepository.save(assignment);
            assignmentIds.add(assignment.getAssignmentId());
        }

        return assignmentIds;

    }

    //평가 배정 삭제
    @Transactional
    public void deleteAssignment(
            Long assignmentId
    ){
        EvaluationAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new BusinessException(EvaluationErrorCode.EVALUATION_ASSIGNMENT_NOT_FOUND)
                );

        if (assignment.isDeleted()) {
            throw new BusinessException(
                    EvaluationErrorCode.EVALUATION_ASSIGNMENT_ALREADY_CANCELED
            );
        }

        assignment.delete();
    }
}
