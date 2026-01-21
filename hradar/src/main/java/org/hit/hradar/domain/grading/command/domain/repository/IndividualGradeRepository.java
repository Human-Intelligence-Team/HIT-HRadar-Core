package org.hit.hradar.domain.grading.command.domain.repository;

import org.hit.hradar.domain.grading.command.domain.aggregate.IndividualGrade;

import java.util.Optional;

public interface IndividualGradeRepository {

    boolean existsByCycleIdAndEmpIdAndIsDeleted(
        Long cycleId,
        Long empId,
        Character isDeleted
);

    Optional<IndividualGrade> findById(Long individualGradeId);

    IndividualGrade save(IndividualGrade individualGrade);
}
