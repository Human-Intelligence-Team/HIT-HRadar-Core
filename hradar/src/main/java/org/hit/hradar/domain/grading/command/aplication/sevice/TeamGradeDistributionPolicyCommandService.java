package org.hit.hradar.domain.grading.command.aplication.sevice;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterTeamGradeDistributionPolicyRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.UpdateTeamGradeDistributionPolicyRequestDto;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.aggregate.TeamGradeDistributionPolicy;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.command.domain.repository.TeamGradeDistributionPolicyRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamGradeDistributionPolicyCommandService {
    private final GradeRepository gradeRepository;
    private final TeamGradeDistributionPolicyRepository policyRepository;

    // 등록
    public void register(
            Long companyId,
            Long teamGradeId,
            RegisterTeamGradeDistributionPolicyRequestDto request
    ) {
        validateGrade(companyId, teamGradeId);
        validateGrade(companyId, request.getMemberGradeId());
        validateRatio(request.getMinRatio(), request.getMaxRatio());

        if (policyRepository.existsByCompanyIdAndTeamGradeIdAndMemberGradeIdAndIsDeleted(
                companyId, teamGradeId, request.getMemberGradeId(), 'N')) {
            throw new BusinessException(
                    GradingErrorCode.DISTRIBUTION_RULE_ALREADY_EXISTS
            );
        }

        TeamGradeDistributionPolicy policy =
                TeamGradeDistributionPolicy.builder()
                        .companyId(companyId)
                        .teamGradeId(teamGradeId)
                        .memberGradeId(request.getMemberGradeId())
                        .minRatio(request.getMinRatio())
                        .maxRatio(request.getMaxRatio())
                        .build();

        policyRepository.save(policy);
    }

    // 수정
    public void update(
            Long companyId,
            Long policyId,
            UpdateTeamGradeDistributionPolicyRequestDto request
    ) {
        TeamGradeDistributionPolicy policy =
                policyRepository.findByPolicyIdAndIsDeleted(policyId, 'N')
                        .orElseThrow(() ->
                                new BusinessException(
                                        GradingErrorCode.DISTRIBUTION_RULE_NOT_FOUND));

        if (!policy.getCompanyId().equals(companyId)) {
            throw new BusinessException(
                    GradingErrorCode.GRADE_COMPANY_MISMATCH
            );
        }

        validateRatio(request.getMinRatio(), request.getMaxRatio());

        policy.updateRatio(
                request.getMinRatio(),
                request.getMaxRatio()
        );
    }

    // 삭제
    public void delete(
            Long companyId,
            Long policyId
    ) {
        TeamGradeDistributionPolicy policy =
                policyRepository.findByPolicyIdAndIsDeleted(policyId, 'N')
                        .orElseThrow(() ->
                                new BusinessException(
                                        GradingErrorCode.DISTRIBUTION_RULE_NOT_FOUND));

        if (!policy.getCompanyId().equals(companyId)) {
            throw new BusinessException(
                    GradingErrorCode.GRADE_COMPANY_MISMATCH
            );
        }

        policy.delete();
    }

    // ===== 공통 검증 =====

    private void validateGrade(Long companyId, Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() ->
                        new BusinessException(
                                GradingErrorCode.GRADE_NOT_FOUND));

        if (!grade.getCompanyId().equals(companyId)) {
            throw new BusinessException(
                    GradingErrorCode.GRADE_COMPANY_MISMATCH
            );
        }
    }

    private void validateRatio(Integer min, Integer max) {
        if (min < 0 || max > 100 || min > max) {
            throw new BusinessException(
                    GradingErrorCode.INVALID_DISTRIBUTION_RATIO
            );
        }
    }
}
