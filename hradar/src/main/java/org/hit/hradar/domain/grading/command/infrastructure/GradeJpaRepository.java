package org.hit.hradar.domain.grading.command.infrastructure;

import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeJpaRepository extends JpaRepository<Grade,Long>, GradeRepository {
}
