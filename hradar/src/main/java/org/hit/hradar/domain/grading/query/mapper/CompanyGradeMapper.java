package org.hit.hradar.domain.grading.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.grading.query.dto.response.CompanyGradeWithRuleListResponseDto;

import java.util.List;

@Mapper
public interface CompanyGradeMapper {

    List<CompanyGradeWithRuleListResponseDto> findCompanyGradesWithRule (
            @Param("companyId") Long companyId
    );
}
