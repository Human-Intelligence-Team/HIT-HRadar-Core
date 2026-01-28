package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleDetailResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleListResponseDto;
import org.hit.hradar.domain.evaluation.query.mapper.CycleMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CycleQueryService {

    private final CycleMapper cycleMapper;

    /* 평가회차 목록 조회 (상세조회는 없음)*/
    @Transactional(readOnly = true)
    public List<CycleListResponseDto> getCycleList() {
        return cycleMapper.selectCycleList();
    }

    /* 평가 회차 상세*/
    @Transactional(readOnly = true)
    public CycleDetailResponseDto getCycleDetail(Long cycleId) {

        CycleDetailResponseDto cycle =
                cycleMapper.selectCycleDetail(cycleId);

        if (cycle == null) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND);
        }

        List<String> evalTypes =
                cycleMapper.selectEvaluationTypesByCycleId(cycleId);

        return new CycleDetailResponseDto(
                cycle.getCycleId(),
                cycle.getCycleName(),
                cycle.getQuarter(),
                cycle.getIs_comp_report_generated(),
                cycle.getStartDate(),
                cycle.getEndDate(),
                cycle.getStatus(),
                cycle.getEmpId(),
                evalTypes
        );
    }
}
