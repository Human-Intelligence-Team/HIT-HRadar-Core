package org.hit.hradar.domain.grading.command.infrastructure;

import org.hit.hradar.domain.grading.command.domain.aggregate.GradeDistributionRule;
import org.hit.hradar.domain.grading.command.domain.repository.GradeDistributionRuleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeDistributionRuleJpaRepository extends JpaRepository<GradeDistributionRule, Long>, GradeDistributionRuleRepository {
}
