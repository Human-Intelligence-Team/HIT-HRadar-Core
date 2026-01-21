package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationAssignmentCreateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationTypeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvaluationAssignmentCommandService {

    private final EvaluationAssignmentRepository assignmentRepository;
    private final EvaluationTypeRepository evaluationTypeRepository;
    private final CycleRepository cycleRepository;
    private final CycleStatusService cycleStatusService;

    /*평가 배정 생성*/
    @Transactional
    public void createAssignments(
            Long evalTypeId,
            EvaluationAssignmentCreateRequest request
    ){
        //평가 유형 조회
        EvaluationType evaluationType = evaluationTypeRepository.findById(evalTypeId)
                .orElseThrow(() ->
                        new BusinessException(EvaluationErrorCode.EVALUATION_TYPE_NOT_FOUND));

        //회차 조회
        Long cycleId = evaluationType.getCycleId();
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        //회차 상태 검증 (DRAFT 시에만 가능)
        cycleStatusService.validateCanConfigureCycle(cycle);

        Long evaluatorId = request.getEvaluatorId();

        for (Long evaluateeId : request.getEvaluateeIds()) {
            //자신 평가 방지
            if (evaluatorId.equals(evaluateeId)) {
                throw new BusinessException(
                        EvaluationErrorCode.EVALUATION_ASSIGNMENT_SELF_NOT_ALLOWED
                );
            }

            //중복 배정 방지
            boolean exists =
                    assignmentRepository.existsByEvaluationTypeAndEvaluatorIdAndEvaluateeId(
                            evaluationType,
                            evaluatorId,
                            evaluateeId
                    );
            if (exists) {
                throw new BusinessException(
                        EvaluationErrorCode.EVALUATION_ASSIGNMENT_ALREADY_EXISTS
                );
            }

            //저장
            EvaluationAssignment assignment =
                    EvaluationAssignment.builder()
                            .evaluationType(evaluationType)
                            .evaluatorId(evaluatorId)
                            .evaluateeId(evaluateeId)
                            .build();

            assignmentRepository.save(assignment);
        }

    }

    //평가 배정 삭제
    @Transactional
    public void deleteAssignments(
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
