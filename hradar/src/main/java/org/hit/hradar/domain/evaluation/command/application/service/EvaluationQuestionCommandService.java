package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.ObjectiveOptionRequestDto;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.*;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationQuestionRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationSectionRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationQuestionCommandService {

    private final EvaluationQuestionRepository questionRepository;
    private final EvaluationSectionRepository sectionRepository;
    private final CycleRepository cycleRepository;
    private final CycleStatusService cycleStatusService;

    /* 문항 생성 */
    @Transactional
    public void createQuestion(Long sectionId, EvaluationQuestionCreateRequest request) {

        EvaluationSection section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_SECTION_NOT_FOUND));

        Long cycleId = section.getEvaluationType().getCycleId();

        Cycle cycle = cycleRepository.findById(cycleId).orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        cycleStatusService.validateCanConfigureCycle(cycle);

        RequiredStatus requiredStatus
                = (request.getRequiredStatus() == null) ?RequiredStatus.OPTIONAL : request.getRequiredStatus();

        //타입별 검증
        validateCreateRequest(request);

        EvaluationQuestion question = EvaluationQuestion.builder()
                .section(section)
                .questionType(request.getQuestionType())
                .questionContent(request.getQuestionContent())
                .requiredStatus(requiredStatus)
                .ratingScale(request.getRatingScale())
                .build();

        //객관식 옵션 붙이기
        if (request.getQuestionType() == QuestionType.OBJECTIVE) {
            for (ObjectiveOptionRequestDto opt : request.getOptions()) {
                ObjectiveOption option = ObjectiveOption.builder()
                        .optionContent(opt.getOptionContent())
                        .optionScore(opt.getOptionScore())
                        .build();
                question.addOption(option);
            }
        }

        questionRepository.save(question);
    }

    //문항 수정
    @Transactional
    public void updateQuestion(Long questionId, EvaluationQuestionUpdateRequest request) {

        EvaluationQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_NOT_FOUND));

        Long cycleId = question.getSection().getEvaluationType().getCycleId();

        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        cycleStatusService.validateCanConfigureCycle(cycle);

        //공통 업데이트
        if (request.getQuestionContent() != null) {
            question.updateContent(request.getQuestionContent());
        }
        if (request.getRequiredStatus() != null) {
            question.updateRequiredStatus(request.getRequiredStatus());
        }

        //타입별 업데이트
        validateUpdateRequest(question.getQuestionType(), request);

        if (question.getQuestionType() == QuestionType.RATING) {
            question.updateRatingScale(request.getRatingScale());
        }

        if (question.getQuestionType() == QuestionType.OBJECTIVE) {
            // 옵션 전체 교체
            question.clearOptions();
            List<ObjectiveOptionRequestDto> options = request.getOptions();
            for (ObjectiveOptionRequestDto opt : options) {
                ObjectiveOption option = ObjectiveOption.builder()
                        .optionContent(opt.getOptionContent())
                        .optionScore(opt.getOptionScore())
                        .build();
                question.addOption(option);
            }
        }
    }

    //문항 삭제
    @Transactional
    public void deleteQuestion(Long questionId) {
        EvaluationQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_NOT_FOUND));

        Long cycleId = question.getSection().getEvaluationType().getCycleId();

        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        cycleStatusService.validateCanConfigureCycle(cycle);

        questionRepository.delete(question);
    }

    //생성 검증 로직==================================================
    private void validateCreateRequest(EvaluationQuestionCreateRequest request) {
        if (request.getQuestionType() == null) {
            throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_INVALID_TYPE);
        }

        switch (request.getQuestionType()) {
            case RATING -> {
                if (request.getRatingScale() == null || request.getRatingScale() <= 0) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_INVALID_RATING_SCALE);
                }
                if (request.getOptions() != null && !request.getOptions().isEmpty()) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_OPTIONS_NOT_ALLOWED);
                }
            }
            case OBJECTIVE -> {
                if (request.getOptions() == null || request.getOptions().size() < 2) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_OPTIONS_REQUIRED);
                }
                if (request.getRatingScale() != null) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_RATING_NOT_ALLOWED);
                }
            }
            case SUBJECTIVE -> {
                if (request.getOptions() != null && !request.getOptions().isEmpty()) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_OPTIONS_NOT_ALLOWED);
                }
                if (request.getRatingScale() != null) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_RATING_NOT_ALLOWED);
                }
            }
        }
    }

    private void validateUpdateRequest(QuestionType type, EvaluationQuestionUpdateRequest request) {
        switch (type) {
            case RATING -> {
                if (request.getRatingScale() == null || request.getRatingScale() <= 0) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_INVALID_RATING_SCALE);
                }
                if (request.getOptions() != null && !request.getOptions().isEmpty()) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_OPTIONS_NOT_ALLOWED);
                }
            }
            case OBJECTIVE -> {
                if (request.getOptions() == null || request.getOptions().size() < 2) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_OPTIONS_REQUIRED);
                }
                if (request.getRatingScale() != null) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_RATING_NOT_ALLOWED);
                }
            }
            case SUBJECTIVE -> {
                if (request.getOptions() != null && !request.getOptions().isEmpty()) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_OPTIONS_NOT_ALLOWED);
                }
                if (request.getRatingScale() != null) {
                    throw new BusinessException(EvaluationErrorCode.EVALUATION_QUESTION_RATING_NOT_ALLOWED);
                }
            }
        }
    }

}
