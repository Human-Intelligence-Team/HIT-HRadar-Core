package org.hit.hradar.domain.grading.command.domain.repository;

import org.hit.hradar.domain.grading.command.domain.aggregate.GradeDistributionRule;

import java.util.List;
import java.util.Optional;

public interface GradeDistributionRuleRepository {
    boolean existsByGradeIdAndIsDeleted(Long gradeId, Character isDeleted);

    GradeDistributionRule save(GradeDistributionRule rule);

    Optional<GradeDistributionRule> findByGradeIdAndIsDeleted(
            Long gradeId,
            Character isDeleted
    );
}
