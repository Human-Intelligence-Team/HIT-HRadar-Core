package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleEvaluationTypeSaveRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CycleEvaluationTypeCommandService {
    private final CycleRepository cycleRepository;
    private final CycleEvaluationTypeRepository cycleEvaluationTypeRepository;


    // 회차별 평가 유형 저장
    public void save(Long cycleId, CycleEvaluationTypeSaveRequest request) {

        //Cycle 조회
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));

        /*//DRAFT 상태면 수정 가능
        if (cycle.getStatus() != org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus.DRAFT) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_NOT_EDITABLE);
        }*/

        //기존 연결된 평가 유형 조회
        List<CycleEvaluationType> existingList =
                cycleEvaluationTypeRepository.findByCycleIdAndIsDeleted(cycleId, 'N');

        Set<Long> existingEvalTypeIds = existingList.stream()
                .map(CycleEvaluationType::getEvalTypeId)
                .collect(Collectors.toSet());

        Set<Long> requestEvalTypeIds = new HashSet<>(request.getEvalTypeIds());

        //기존에 있었는데 이번 요청엔 없는 것
        existingList.stream()
                .filter(it -> !requestEvalTypeIds.contains(it.getEvalTypeId()))
                .forEach(CycleEvaluationType::delete);

        //추가대상
        requestEvalTypeIds.stream()
                .filter(evalTypeId -> !existingEvalTypeIds.contains(evalTypeId))
                .forEach(evalTypeId -> {
                    CycleEvaluationType newMapping =
                            CycleEvaluationType.builder()
                                    .cycleId(cycleId)
                                    .evalTypeId(evalTypeId)
                                    .build();
                    cycleEvaluationTypeRepository.save(newMapping);
                });
    }
}
