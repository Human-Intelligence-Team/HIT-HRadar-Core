package org.hit.hradar.domain.grading.query.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.grading.query.dto.response.DeptGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.mapper.DeptGradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DeptGradeQueryService {

    private final DeptGradeMapper deptGradeMapper;

    public List<DeptGradeStatusResponseDto> getDeptGradeStatusList(
            Long companyId,
            Long cycleId
    ) {
        log.info(">>> SQL PARAM CHECK companyId={}, cycleId={}", companyId, cycleId);

        return deptGradeMapper.findDeptGradeStatusList(
                companyId,
                cycleId
        );
    }
}
