package org.hit.hradar.domain.grading.query.service;

import org.hit.hradar.domain.grading.query.dto.response.TeamGradeDistributionPolicyDto;
import org.hit.hradar.domain.grading.query.mapper.TeamGradeDistributionPolicyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamGradeDistributionPolicyQueryServiceTest {

    @InjectMocks
    private TeamGradeDistributionPolicyQueryService service;

    @Mock
    private TeamGradeDistributionPolicyMapper mapper;

    @Test
    @DisplayName("팀 등급 배분 정책 목록 조회 - 성공")
    void getPolicies_success() {
        // given
        Long companyId = 1L;
        Long teamGradeId = 10L;
        TeamGradeDistributionPolicyDto dto = new TeamGradeDistributionPolicyDto();

        when(mapper.findByTeamGrade(companyId, teamGradeId)).thenReturn(List.of(dto));

        // when
        List<TeamGradeDistributionPolicyDto> result = service.getPolicies(companyId, teamGradeId);

        // then
        assertThat(result).hasSize(1);
        verify(mapper).findByTeamGrade(companyId, teamGradeId);
    }

    @Test
    @DisplayName("팀 등급 배분 정책 단건 조회 - 성공")
    void getPolicy_success() {
        // given
        Long companyId = 1L;
        Long teamGradeId = 10L;
        Long memberGradeId = 20L;
        TeamGradeDistributionPolicyDto dto = new TeamGradeDistributionPolicyDto();

        when(mapper.findOne(companyId, teamGradeId, memberGradeId)).thenReturn(dto);

        // when
        TeamGradeDistributionPolicyDto result = service.getPolicy(companyId, teamGradeId, memberGradeId);

        // then
        assertThat(result).isEqualTo(dto);
        verify(mapper).findOne(companyId, teamGradeId, memberGradeId);
    }
}
