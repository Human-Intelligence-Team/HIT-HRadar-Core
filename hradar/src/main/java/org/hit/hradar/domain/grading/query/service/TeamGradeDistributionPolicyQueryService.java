package org.hit.hradar.domain.grading.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.TeamGradeDistributionPolicyDto;
import org.hit.hradar.domain.grading.query.mapper.TeamGradeDistributionPolicyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamGradeDistributionPolicyQueryService {

    private final TeamGradeDistributionPolicyMapper mapper;

    public List<TeamGradeDistributionPolicyDto> getPolicies(
            Long companyId,
            Long teamGradeId
    ) {
        return mapper.findByTeamGrade(companyId, teamGradeId);
    }

    public TeamGradeDistributionPolicyDto getPolicy(
            Long companyId,
            Long teamGradeId,
            Long memberGradeId
    ) {
        TeamGradeDistributionPolicyDto dto =
                mapper.findOne(companyId, teamGradeId, memberGradeId);

        return dto;
    }
}
