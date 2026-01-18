package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CycleStatusService {

    private final CycleRepository cycleRepository;

    //시간 경과에 따른 상태 자동 반영
    public void autoUpdateCycleStatus(){
        LocalDateTime now = LocalDateTime.now();

        //승인 되었거나 이미 열린 회차만 대상
        List<Cycle> cycles = cycleRepository.findAllByStatusIn(
                List.of(CycleStatus.APPROVED, CycleStatus.IN_PROGRESS)
        );

        for (Cycle cycle : cycles) {

            //종료일 경과 -> CLOSED
            if(now.isAfter(cycle.getEndDate())) {
                cycle.close();
                continue;
            }

            if (cycle.getStatus() != CycleStatus.APPROVED) {
                continue;
            }

            if (!now.isBefore(cycle.getStartDate())) {
                cycle.open();
            }
        }
    }

    //현재 IN_PROGRESS 상태인지 여부
    public boolean isOpen(Cycle cycle) {
        return calculateStatus(cycle) == CycleStatus.IN_PROGRESS;
    }

    //현재 시점 기준으로 계산된 상태 반환
    public CycleStatus calculateStatus(Cycle cycle) {
        LocalDateTime now = LocalDateTime.now();

        //종료일 경과 CLOSED
        if(now.isAfter(cycle.getEndDate())) {
            return CycleStatus.CLOSED;
        }

        //APPROVED + 시작일 도래 -> IN_PROGRESS
        if (cycle.getStatus() == CycleStatus.APPROVED &&
                !now.isBefore(cycle.getStartDate())){
            return CycleStatus.IN_PROGRESS;
        }

        //APPROVED이지만 시작 전
        if (cycle.getStatus() == CycleStatus.APPROVED) {
            return CycleStatus.APPROVED;
        }

        //승인되지 않으면 무조건 DRAFT
        return CycleStatus.DRAFT;
    }

    //회사 구성 가능 여부
    public void validateCanConfigureCycle(Cycle cycle) {

        if (cycle.getStatus() != CycleStatus.DRAFT) {
            throw new BusinessException(
                    EvaluationErrorCode.CYCLE_CONFIGURATION_NOT_ALLOWED
            );
        }
    }
}
