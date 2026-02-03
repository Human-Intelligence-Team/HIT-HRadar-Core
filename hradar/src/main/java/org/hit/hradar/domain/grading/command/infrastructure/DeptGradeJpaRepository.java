package org.hit.hradar.domain.grading.command.infrastructure;

import org.hit.hradar.domain.grading.command.domain.aggregate.DeptGrade;
import org.hit.hradar.domain.grading.command.domain.repository.DeptGradeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptGradeJpaRepository extends JpaRepository<DeptGrade,Integer>, DeptGradeRepository {
}
