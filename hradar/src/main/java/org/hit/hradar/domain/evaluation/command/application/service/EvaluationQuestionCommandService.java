package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.ObjectiveOptionRequestDto;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.*;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationQuestionRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationSectionRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationQuestionCommandService {
    private final EvaluationQuestionRepository questionRepository;
    private final EvaluationSectionRepository sectionRepository;
    private final CycleRepository cycleRepository;

    //문제 생성
    public Long create(EvaluationQuestionCreateRequest request){

        //섹션 조회
        EvaluationSection section =
                sectionRepository.findById(request.getSectionId())
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.EVALUATION_SECTION_NOT_FOUND)
                        );

        //섹션 -> cycleEvaluationType조회
        CycleEvaluationType cycleEvalType =
                section.getCycleEvaluationType();


        //Cycle 조회
        Cycle cycle =
                cycleRepository.findById(cycleEvalType.getCycleId())
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND)
                        );


        //회차 상태 검증(Draft만)
        if (cycle.getStatus() != CycleStatus.DRAFT) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_CONFIGURATION_NOT_ALLOWED);
        }

        // Question 생성
        EvaluationQuestion question = EvaluationQuestion.builder()
                .section(section)
                .questionType(request.getQuestionType())
                .questionContent(request.getQuestionContent())
                .ratingScale(request.getRatingScale())
                .requiredStatus(request.getRequiredStatus())
                .build();

        // 객관식 옵션 처리
        if (request.getQuestionType() == QuestionType.OBJECTIVE) {
            request.getOptions().forEach(opt ->
                    question.addOption(
                            ObjectiveOption.builder()
                                    .optionContent(opt.getOptionContent())
                                    .optionScore(opt.getOptionScore())
                                    .build()
                    )
            );
        }

        // 저장
        questionRepository.save(question);

        return question.getQuestionId();
    }

    //수정
    public void update(Long questionId, EvaluationQuestionUpdateRequest request) {

        EvaluationQuestion question =
                questionRepository.findById(questionId)
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_NOT_FOUND)
                        );

        EvaluationSection section = question.getSection();

        CycleEvaluationType cycleEvalType =
                section.getCycleEvaluationType();

        Cycle cycle =
                cycleRepository.findById(cycleEvalType.getCycleId())
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND)
                        );

        if (cycle.getStatus() != CycleStatus.DRAFT) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_CONFIGURATION_NOT_ALLOWED);
        }

        // 기본 정보 수정
        if (request.getQuestionContent() != null) {
            question.updateContent(request.getQuestionContent());
        }
        if (request.getRequiredStatus() != null) {
            question.updateRequiredStatus(request.getRequiredStatus());
        }

        if (question.getQuestionType() == QuestionType.RATING) {
            question.updateRatingScale(request.getRatingScale());
        }

        if (question.getQuestionType() == QuestionType.OBJECTIVE) {

            question.clearOptions();

            request.getOptions().forEach(opt ->
                    question.addOption(
                            ObjectiveOption.builder()
                                    .optionContent(opt.getOptionContent())
                                    .optionScore(opt.getOptionScore())
                                    .build()
                    )
            );
        }
    }

    //삭제
    public void deleteQuestion(Long questionId) {
        EvaluationQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_NOT_FOUND));

        questionRepository.delete(question);
    }
}
