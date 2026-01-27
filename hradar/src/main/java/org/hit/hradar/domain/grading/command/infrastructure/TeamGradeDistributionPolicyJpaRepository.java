package org.hit.hradar.domain.grading.command.infrastructure;

import org.hit.hradar.domain.grading.command.domain.aggregate.TeamGradeDistributionPolicy;
import org.hit.hradar.domain.grading.command.domain.repository.TeamGradeDistributionPolicyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamGradeDistributionPolicyJpaRepository
        extends JpaRepository<TeamGradeDistributionPolicy, Long>, TeamGradeDistributionPolicyRepository {
}
