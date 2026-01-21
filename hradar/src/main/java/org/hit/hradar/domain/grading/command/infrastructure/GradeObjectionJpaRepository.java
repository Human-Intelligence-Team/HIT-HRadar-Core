package org.hit.hradar.domain.grading.command.infrastructure;

import org.hit.hradar.domain.grading.command.domain.aggregate.GradeObjection;
import org.hit.hradar.domain.grading.command.domain.repository.GradeObjectionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeObjectionJpaRepository extends JpaRepository<GradeObjection, Long>, GradeObjectionRepository {
}
