package org.hit.hradar.domain.grading.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.grading.query.dto.response.MyIndividualGradeResponseDto;

@Mapper
public interface MyIndividualGradeMapper {

    MyIndividualGradeResponseDto findMyIndividualGrade(
            @Param("empId") Long empId,
            @Param("cycleId") Long cycleId
    );
}
