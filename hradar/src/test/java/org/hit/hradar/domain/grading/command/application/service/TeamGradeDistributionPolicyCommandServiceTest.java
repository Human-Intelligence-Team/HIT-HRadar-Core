package org.hit.hradar.domain.grading.command.application.service;

import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterTeamGradeDistributionPolicyRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.TeamGradeDistributionPolicyCommandService;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.aggregate.TeamGradeDistributionPolicy;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.command.domain.repository.TeamGradeDistributionPolicyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamGradeDistributionPolicyCommandServiceTest {

    @InjectMocks
    private TeamGradeDistributionPolicyCommandService service;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private TeamGradeDistributionPolicyRepository policyRepository;

    @Test
    @DisplayName("팀 등급 배분 정책 등록 - 성공")
    void register_success() {
        // given
        Long companyId = 1L;
        Long teamGradeId = 10L;
        RegisterTeamGradeDistributionPolicyRequestDto request = new RegisterTeamGradeDistributionPolicyRequestDto();
        setField(request, "memberGradeId", 20L);
        setField(request, "minRatio", 10);
        setField(request, "maxRatio", 20);

        Grade teamGrade = mock(Grade.class);
        when(teamGrade.getCompanyId()).thenReturn(companyId);
        when(gradeRepository.findById(teamGradeId)).thenReturn(Optional.of(teamGrade));

        Grade memberGrade = mock(Grade.class);
        when(memberGrade.getCompanyId()).thenReturn(companyId);
        when(gradeRepository.findById(20L)).thenReturn(Optional.of(memberGrade));

        when(policyRepository.existsByCompanyIdAndTeamGradeIdAndMemberGradeIdAndIsDeleted(
                companyId, teamGradeId, 20L, 'N')).thenReturn(false);

        // when
        service.register(companyId, teamGradeId, request);

        // then
        verify(policyRepository).save(any(TeamGradeDistributionPolicy.class));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
