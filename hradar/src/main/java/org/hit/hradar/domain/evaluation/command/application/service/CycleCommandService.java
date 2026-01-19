package org.hit.hradar.domain.evaluation.command.application.service;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleCreateRequestDto;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleUpdateRequestDto;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CycleCommandService {

    private final CycleRepository cycleRepository;

    /*평가 회차 생성*/
    public void createCycle(CycleCreateRequestDto request, Long empId) {
        validatePeriod(request.getStartDate(), request.getEndDate());

        Cycle cycle = Cycle.builder()
                .year(request.getYear())
                .quarter(request.getQuarter())
                .cycleName(request.getCycleName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .empId(empId)
                .build();

        cycleRepository.save(cycle);
    }

    /*평가 기간 수정*/
    public void updatePeriod(Long cycleId, CycleUpdateRequestDto request) {
        Cycle cycle = getCycle(cycleId);

        validateDeleted(cycle);

        if (cycle.isClosed()) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_ALREADY_CLOSED);
        }

        validatePeriod(request.getStartDate(), request.getEndDate());
        cycle.updateCycle(request.getYear(), request.getQuarter(),request.getCycleName(), request.getStartDate(), request.getEndDate());
    }

    /* 평가 회차 강제 마감*/
    public void forceClose(Long cycleId) {
        Cycle cycle = getCycle(cycleId);

        validateDeleted(cycle);

        if (cycle.isClosed()) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_ALREADY_CLOSED);
        }

        cycle.closeCycle();
    }

    /*회차 삭제*/
    public void deleteCycle(Long cycleId) {
        Cycle cycle = getCycle(cycleId);

        validateDeleted(cycle);

        cycle.deleteCycle();
    }

    /*회차 승인*/
    public void approveCycle(Long cycleId) {
        Cycle cycle = getCycle(cycleId);

        validateDeleted(cycle);

        cycle.approveCycle();

    }


    private Cycle getCycle(Long cycleId) {
        return cycleRepository.findById(cycleId)
                .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND));
    }

    private void validatePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_INVALID_PERIOD);
        }
    }

    private void validateDeleted (Cycle cycle) {
        if (cycle.getIsDeleted() == 'Y') {
            throw new BusinessException(EvaluationErrorCode.CYCLE_DELETED);
        }
    }

}
