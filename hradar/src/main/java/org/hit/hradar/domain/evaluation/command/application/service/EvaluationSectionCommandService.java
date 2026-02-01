package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationSection;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationSectionRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationTypeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationSectionCommandService {

    private final EvaluationSectionRepository evaluationSectionRepository;
    private final CycleEvaluationTypeRepository cycleEvaluationTypeRepository;


    /*섹션 생성*/
    public Long create(Long cycleEvalTypeId, EvaluationSectionCreateRequest request) {
        CycleEvaluationType cycleEvalType =
                cycleEvaluationTypeRepository.findById(cycleEvalTypeId)
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.CYCLE_EVAL_TYPE_NOT_FOUND)
                        );
        // EvaluationSection 생성
        EvaluationSection section = EvaluationSection.builder()
                .cycleEvaluationType(cycleEvalType)
                .sectionTitle(request.getSectionTitle())
                .sectionDescription(request.getSectionDescription())
                .sectionOrder(request.getSectionOrder())
                .build();

        EvaluationSection saved = evaluationSectionRepository.save(section);

        //생성된 sectionId 반환
        return saved.getSectionId();
    }

    //섹션 수정
    public void update(Long sectionId, EvaluationSectionUpdateRequest request) {

        //기존 섹션 조회
        EvaluationSection section =
                evaluationSectionRepository.findById(sectionId)
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.EVALUATION_SECTION_NOT_FOUND)
                        );

        //변경
        section.update(
                request.getSectionTitle(),
                request.getSectionDescription(),
                request.getSectionOrder()
        );
    }

    //섹션 삭제
    public void delete(Long sectionId) {

        EvaluationSection section =
                evaluationSectionRepository.findById(sectionId)
                        .orElseThrow(() ->
                                new BusinessException(EvaluationErrorCode.EVALUATION_SECTION_NOT_FOUND)
                        );

        section.delete();
    }


}
