package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeUpdateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationTypeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationTypeCommandService {

    private final EvaluationTypeRepository evaluationTypeRepository;

    //평가 유형 생성
    public Long create(Long compId, EvaluationTypeCreateRequest request) {

        boolean exists = evaluationTypeRepository
                .existsByCompanyIdAndTypeNameAndIsDeleted(
                        compId,
                        request.getTypeName(),
                        'N'
                );

        if (exists) {
            throw new BusinessException(EvaluationErrorCode.DUPLICATE_EVALUATION_TYPE);
        }

        EvaluationType evaluationType = EvaluationType.builder()
                .companyId(compId)
                .typeName(request.getTypeName())
                .build();

        return evaluationTypeRepository.save(evaluationType).getEvalTypeId();
    }

    //평가유형 수정
    public void update(Long evalTypeId, EvaluationTypeUpdateRequest request) {

        EvaluationType evaluationType = evaluationTypeRepository.findById(evalTypeId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_TYPE_NOT_FOUND));

        evaluationType.updateName(request.getTypeName());
    }

    //평가 유형 삭제
    public void delete(Long evalTypeId) {

        EvaluationType evaluationType = evaluationTypeRepository.findById(evalTypeId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_TYPE_NOT_FOUND));

        evaluationType.delete();
    }
}
