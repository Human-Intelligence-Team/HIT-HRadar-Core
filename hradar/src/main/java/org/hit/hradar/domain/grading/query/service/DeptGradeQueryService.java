package org.hit.hradar.domain.grading.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.DeptGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.mapper.DeptGradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeptGradeQueryService {

    private final DeptGradeMapper deptGradeMapper;

    public List<DeptGradeStatusResponseDto> getDeptGradeStatusList(
            Long companyId,
            Long cycleId
    ) {
        return deptGradeMapper.findDeptGradeStatusList(
                companyId,
                cycleId
        );
    }
}
