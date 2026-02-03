package org.hit.hradar.domain.grading.query.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.grading.query.dto.response.EmployeeGradeStatusResponseDto;

import java.util.List;

@Mapper
public interface EmployeeGradeMapper {
    List<EmployeeGradeStatusResponseDto> findEmployeeGradeStatusList(
            @Param("companyId") Long companyId,
            @Param("deptId") Long deptId,
            @Param("cycleId") Long cycleId
    );
}
