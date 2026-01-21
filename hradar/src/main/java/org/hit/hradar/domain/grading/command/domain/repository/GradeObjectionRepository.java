package org.hit.hradar.domain.grading.command.domain.repository;

import org.hit.hradar.domain.grading.command.domain.aggregate.GradeObjection;

import java.util.List;
import java.util.Optional;

public interface GradeObjectionRepository {
    GradeObjection save(GradeObjection objection);

    Optional<GradeObjection> findById(Long objectionId);

    List<GradeObjection> findAllByIndividualGradeIdAndIsDeleted(
            Long individualGradeId,
            Character isDeleted
    );
}
