package org.hit.hradar.domain.grading.command.domain.repository;

import org.hit.hradar.domain.grading.command.domain.aggregate.TeamGradeDistributionPolicy;

import java.util.List;
import java.util.Optional;

public interface TeamGradeDistributionPolicyRepository {

    TeamGradeDistributionPolicy save(TeamGradeDistributionPolicy policy);

    boolean existsByCompanyIdAndTeamGradeIdAndMemberGradeIdAndIsDeleted(
            Long companyId,
            Long teamGradeId,
            Long memberGradeId,
            Character isDeleted
    );

    List<TeamGradeDistributionPolicy> findAllByCompanyIdAndTeamGradeIdAndIsDeleted(
            Long companyId,
            Long teamGradeId,
            Character isDeleted
    );

    Optional<TeamGradeDistributionPolicy> findByPolicyIdAndIsDeleted(
            Long policyId,
            Character isDeleted
    );
}
