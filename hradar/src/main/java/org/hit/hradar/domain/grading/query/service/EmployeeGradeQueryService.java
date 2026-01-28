package org.hit.hradar.domain.grading.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.EmployeeGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.mapper.EmployeeGradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeGradeQueryService {

    private final EmployeeGradeMapper employeeGradeMapper;

    public List<EmployeeGradeStatusResponseDto> getEmployeeGradeStatusList(
            Long companyId,
            Long deptId,
            Long cycleId
    ) {
        return employeeGradeMapper.findEmployeeGradeStatusList(
                companyId,
                deptId,
                cycleId
        );
    }
}
