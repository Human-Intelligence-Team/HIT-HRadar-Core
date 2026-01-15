package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleListResponseDto;
import org.hit.hradar.domain.evaluation.query.mapper.CycleMapper;
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


}
