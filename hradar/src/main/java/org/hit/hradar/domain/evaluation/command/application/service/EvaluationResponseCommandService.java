package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.binding.BindingException;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseItemRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseUpsertRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.*;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationQuestionRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationResponseRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvaluationResponseCommandService {
    private final EvaluationResponseRepository evaluationResponseRepository;
    private final EvaluationAssignmentRepository evaluationAssignmentRepository;
    private final EvaluationQuestionRepository evaluationQuestionRepository;

    //임시저장
    @Transactional
    public void saveDraft(EvaluationResponseUpsertRequest request) {
        EvaluationAssignment assignment = evaluationAssignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_ASSIGNMENT_NOT_FOUND));


        //제출된 평가인지 검증
        if (assignment.getStatus() == AssignmentStatus.SUBMITTED) {
            throw new BusinessException(EvaluationErrorCode.EVALUATION_ASSIGNMENT_ALREADY_SUBMITTED);
        }

        for (EvaluationResponseItemRequest item : request.getResponses()) {
            upsertSingleResponse(assignment, item);
        }
    }

    //질문 1개에 대한 응답 upsert
    private void upsertSingleResponse(EvaluationAssignment assignment, EvaluationResponseItemRequest item) {
        EvaluationQuestion question = evaluationQuestionRepository.findById(item.getQuestionId())
                .orElseThrow(()-> new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_NOT_FOUND));

        QuestionType responseType = QuestionType.valueOf(item.getResponseType());

        Integer score = null;
        String textAnswer = null;
        ObjectiveOption selectedOption = null;

        // 타입별 값 세팅
        switch (responseType) {
            case RATING -> score = item.getScore();

            case SUBJECTIVE -> textAnswer = item.getTextAnswer();

            case OBJECTIVE -> {
                if (item.getOptionId() != null) {
                    selectedOption = evaluationQuestionRepository
                            .findOptionByQuestionIdAndOptionId(
                                    question.getQuestionId(),
                                    item.getOptionId()
                            )
                            .orElseThrow(() ->
                                    new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_NOT_FOUND));
                }
            }
        }

        //기존 응답 조회
        EvaluationResponse response = evaluationResponseRepository.findByAssignment_AssignmentIdAndQuestion_QuestionId(
                assignment.getAssignmentId(),
                question.getQuestionId()
        ).orElse(null);


        //없으면 신규 생성
        if (response == null) {
            // 신규 생성
            EvaluationResponse newResponse = EvaluationResponse.builder()
                    .assignment(assignment)
                    .question(question)
                    .responseType(responseType)
                    .score(score)
                    .textAnswer(textAnswer)
                    .selectedOption(selectedOption)
                    .build();

            evaluationResponseRepository.save(newResponse);
        } else {
            // 기존 응답 덮어쓰기
            response.updateAnswer(responseType, score, textAnswer, selectedOption);
        }
    }

}
