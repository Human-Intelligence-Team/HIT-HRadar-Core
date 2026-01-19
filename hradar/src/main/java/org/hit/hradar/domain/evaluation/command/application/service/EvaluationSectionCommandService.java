package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationSection;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationSectionRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationTypeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationSectionCommandService {

    private final EvaluationSectionRepository sectionRepository;
    private final EvaluationTypeRepository evaluationTypeRepository;
    private final CycleRepository cycleRepository;
    private final CycleStatusService cycleStatusService;

    /*섹션 생성*/
    public void createSection(Long cycleId, EvaluationSectionCreateRequest request) {

        // 회차 조회
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        // 회차 상태 검증 (DRAFT만 허용)
        cycleStatusService.validateCanConfigureCycle(cycle);

        // 평가 유형 조회
        EvaluationType evaluationType = evaluationTypeRepository.findById(request.getEvalTypeId())
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_TYPE_NOT_FOUND));


        // 섹션 생성
        EvaluationSection section = EvaluationSection.builder()
                .evaluationType(evaluationType)
                .sectionTitle(request.getSectionTitle())
                .sectionDescription(request.getSectionDescription())
                .sectionOrder(request.getSectionOrder())
                .build();

        sectionRepository.save(section);
    }

    /*섹션 수정*/
    public void updateSection(Long sectionId, EvaluationSectionUpdateRequest request) {

        EvaluationSection section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_SECTION_NOT_FOUND));

        Long cycleId = section.getEvaluationType().getCycleId();

        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        cycleStatusService.validateCanConfigureCycle(cycle);

        section.update(
                request.getSectionTitle(),
                request.getSectionDescription(),
                request.getSectionOrder()
        );
    }

    public void deleteSection(Long sectionId) {

        EvaluationSection section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.EVALUATION_SECTION_NOT_FOUND));

        Long cycleId = section.getEvaluationType().getCycleId();

        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        cycleStatusService.validateCanConfigureCycle(cycle);

        section.delete();
    }

}
