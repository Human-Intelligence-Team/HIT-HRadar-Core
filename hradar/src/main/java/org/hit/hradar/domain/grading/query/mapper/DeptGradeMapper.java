package org.hit.hradar.domain.grading.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.grading.query.dto.response.DeptGradeStatusResponseDto;

import java.util.List;

@Mapper
public interface DeptGradeMapper {

    List<DeptGradeStatusResponseDto> findDeptGradeStatusList(
            @Param("companyId") Long companyId,
            @Param("cycleId") Long cycleId
    );
}
