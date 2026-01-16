package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.OkrListResponseDto;
import org.hit.hradar.domain.goal.query.mapper.OkrListMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OkrListQueryService {

    private final OkrListMapper okrListMapper;

    @Transactional(readOnly = true)
    public List<OkrListResponseDto> getOkrs(Long goalId) {
        return okrListMapper.findOkrsByGoalId(goalId);
    }
}
