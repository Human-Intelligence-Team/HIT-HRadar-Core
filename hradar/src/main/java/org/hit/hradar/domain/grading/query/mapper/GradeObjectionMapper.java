package org.hit.hradar.domain.grading.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionAdminResponseDto;
import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionResponseDto;

import java.util.List;

@Mapper
public interface GradeObjectionMapper {
    List<GradeObjectionResponseDto> findByIndividualGradeId(
            @Param("individualGradeId") Long individualGradeId
    );

    List<GradeObjectionAdminResponseDto> findByDepartment(
            @Param("companyId") Long companyId,
            @Param("deptId") Long deptId
    );

    List<GradeObjectionAdminResponseDto> findAllByCompany(
            @Param("companyId") Long companyId
    );
}
