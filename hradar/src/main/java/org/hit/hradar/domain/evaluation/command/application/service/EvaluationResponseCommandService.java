package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseDraftRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseSaveRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.*;
import org.hit.hradar.domain.evaluation.command.domain.repository.*;
import org.hit.hradar.domain.evaluation.command.infrastructure.CycleJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationResponseCommandService {

    private final EvaluationAssignmentRepository  assignmentRepository;
    private final EvaluationResponseRepository responseRepository;
    private final EvaluationQuestionRepository  questionRepository;
    private final CycleStatusService cycleStatusService;
    private final CycleRepository cycleRepository;
    private final ObjectiveOptionRepository optionRepository;

    //임시 저장
    @Transactional
    public void saveDraft(
            EvaluationResponseDraftRequest request,
            Long employeeId
    ){
        //할당 조회
        EvaluationAssignment assignment =
                assignmentRepository.findById(request.getAssignmentId())
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.EVALUATION_ASSIGNMENT_NOT_FOUND));

        //삭제, 제출 여부 검증
        if (assignment.isDeleted()) {
            throw new BusinessException(EvaluationErrorCode.EVALUATION_ASSIGNMENT_DELETED);
        }

        if (assignment.getStatus() == AssignmentStatus.SUBMITTED) {
            throw new BusinessException(EvaluationErrorCode.EVALUATION_ASSIGNMENT_ALREADY_SUBMITTED);
        }

        //본인 평가 여부 검증
        if (!assignment.getEvaluatorId().equals(employeeId)) {
            throw new BusinessException(EvaluationErrorCode.EVALUATION_ASSIGNMENT_FORBIDDEN);
        }

        //회차 OPEN 검증
        Long cycleId = assignment.getEvaluationType().getCycleId();
        Cycle cycle = cycleRepository.findById(cycleId).orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));
        if(cycleStatusService.calculateStatus(cycle) != CycleStatus.IN_PROGRESS) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_NOT_OPEN);
        }

        //응답 저장
        for (EvaluationResponseSaveRequest r : request.getResponse()) {

            EvaluationQuestion question =
                    questionRepository.findById(r.getQuestionId())
                            .orElseThrow(() ->
                                    new BusinessException(
                                            EvaluationErrorCode.EVALUATION_QUESTION_NOT_FOUND
                                    )
                            );

            EvaluationResponse response =
                    responseRepository
                            .findByAssignment_AssignmentIdAndQuestion_QuestionId(
                                    assignment.getAssignmentId(),
                                    question.getQuestionId()
                            )
                            .orElseGet(() ->
                                    EvaluationResponse.builder()
                                            .assignment(assignment)
                                            .question(question)
                                            .build()
                            );

            //문항별 값 세팅
            switch (question.getQuestionType()) {
                case OBJECTIVE -> {
                    if (r.getOptionId() == null) {
                        throw new BusinessException(
                                EvaluationErrorCode.EVALUATION_QUESTION_OPTIONS_REQUIRED
                        );
                    }

                    ObjectiveOption option =
                            optionRepository.findById(r.getOptionId())
                                    .orElseThrow(() ->
                                            new BusinessException(
                                                    EvaluationErrorCode.EVALUATION_OPTION_NOT_FOUND
                                            )
                                    );

                    response.updateObjective(option);
                }
                case RATING -> {
                    response.updateRating(r.getScore());
                }
                case SUBJECTIVE -> {
                    response.updateText(r.getText());
                }
                default -> throw new BusinessException(
                        EvaluationErrorCode.EVALUATION_QUESTION_INVALID_TYPE
                );
            }

            responseRepository.save(response);
        }
    }

    /*평가 제출*/
    @Transactional
    public void submit(
            EvaluationResponseDraftRequest request,
            Long employeeId
    ) {
        // 임시저장 로직 재사용
        saveDraft(request, employeeId);

        // 배정 조회
        EvaluationAssignment assignment =
                assignmentRepository.findById(request.getAssignmentId())
                        .orElseThrow(() ->
                                new BusinessException(
                                        EvaluationErrorCode.EVALUATION_ASSIGNMENT_NOT_FOUND
                                )
                        );

        //필수 문항 응답 여부 검증
        validateAllRequiredAnswered(assignment);

        //제출 처리
        assignment.submit();
    }

    private void validateAllRequiredAnswered(EvaluationAssignment assignment) {

        Long evalTypeId = assignment.getEvaluationType().getEvalTypeId();

        // 전체 문항
        List<EvaluationQuestion> questions =
                questionRepository.findAllBySection_EvaluationType_EvalTypeId(evalTypeId);

        // 필수 문항 수
        long requiredCount = questions.stream()
                .filter(q -> q.getRequiredStatus() == RequiredStatus.REQUIRED)
                .count();

        // 응답 수
        long answeredCount =
                responseRepository.countByAssignment_AssignmentId(
                        assignment.getAssignmentId()
                );

        if (answeredCount < requiredCount) {
            throw new BusinessException(
                    EvaluationErrorCode.EVALUATION_ASSIGNMENT_INCOMPLETE
            );
        }
    }

}
