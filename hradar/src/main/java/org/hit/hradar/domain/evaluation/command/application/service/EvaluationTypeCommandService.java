package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeAddRequest;
import org.hit.hradar.domain.evaluation.command.application.port.CommonCodeValidationService;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationTypeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationTypeCommandService {

    private static final String EVAL_TYPE_GROUP = "EVAL_TYPE";

    private final EvaluationTypeRepository evaluationTypeRepository;
    private final CycleRepository cycleRepository;
    private final CycleStatusService cycleStatusService;
/*
    private final CommonCodeValidationService commonCodeValidationService;
*/


    /*회차에 평가 유형 추가*/
    public void addEvaluationType(Long cycleId, EvaluationTypeAddRequest request) {
        //회차 조회
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        //회차 상태 DRAFT여부
        cycleStatusService.validateCanConfigureCycle(cycle);

        /*//공통코드 검증
        commonCodeValidationService.validate(
                EVAL_TYPE_GROUP,
                request.getEvalTypeCode()
        );*/

        //중복 겁증
        boolean exists = evaluationTypeRepository
                .existsByCycleIdAndEvalTypeCodeAndIsDeleted(
                        cycleId,
                        request.getEvalTypeCode(),
                        'N'
                );
        if (exists) {
            throw new BusinessException(EvaluationErrorCode.EVALUATION_TYPE_ALREADY_EXISTS);
        }

        //저장
        EvaluationType evaluationType = EvaluationType.builder()
                .cycleId(cycleId)
                .evalTypeCode(request.getEvalTypeCode())
                .build();

        evaluationTypeRepository.save(evaluationType);
    }

    //평가 유형 제거
    public void removeEvaluationType(Long evalTypeId) {

        EvaluationType evaluationType = evaluationTypeRepository.findById(evalTypeId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_TYPE_NOT_FOUND));

        evaluationType.delete();
    }
}
