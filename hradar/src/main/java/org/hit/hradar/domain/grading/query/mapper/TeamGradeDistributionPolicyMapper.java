package org.hit.hradar.domain.grading.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.grading.query.dto.response.TeamGradeDistributionPolicyDto;

import java.util.List;

@Mapper
public interface TeamGradeDistributionPolicyMapper {

    /**
     * 회사 + 팀 등급 기준 정책 목록 조회
     */
    List<TeamGradeDistributionPolicyDto> findByTeamGrade(
            @Param("companyId") Long companyId,
            @Param("teamGradeId") Long teamGradeId
    );

    /**
     * 단건 조회 (회사 + 팀 등급 + 팀원 등급)
     */
    TeamGradeDistributionPolicyDto findOne(
            @Param("companyId") Long companyId,
            @Param("teamGradeId") Long teamGradeId,
            @Param("memberGradeId") Long memberGradeId
    );
}
