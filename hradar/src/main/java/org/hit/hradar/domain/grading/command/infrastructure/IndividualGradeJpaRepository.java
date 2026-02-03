package org.hit.hradar.domain.grading.command.infrastructure;

import org.hit.hradar.domain.grading.command.domain.aggregate.IndividualGrade;
import org.hit.hradar.domain.grading.command.domain.repository.IndividualGradeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualGradeJpaRepository extends JpaRepository<IndividualGrade, Long>, IndividualGradeRepository {
}
