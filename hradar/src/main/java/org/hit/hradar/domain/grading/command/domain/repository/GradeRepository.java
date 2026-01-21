package org.hit.hradar.domain.grading.command.domain.repository;

import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;

import java.util.Optional;

public interface GradeRepository {
    boolean existsByCompanyIdAndGradeNameAndIsDeleted(
            Long companyId,
            String gradeName,
            Character isDeleted
    );

    boolean existsByCompanyIdAndGradeOrderAndIsDeleted(
            Long companyId,
            Integer gradeOrder,
            Character isDeleted
    );

    Optional<Grade> findByCompanyIdAndGradeNameAndIsDeleted(
            Long companyId,
            String gradeName,
            Character isDeleted
    );

    Grade save(Grade grade);
}
