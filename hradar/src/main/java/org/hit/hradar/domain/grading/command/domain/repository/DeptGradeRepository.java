package org.hit.hradar.domain.grading.command.domain.repository;

import org.hit.hradar.domain.grading.command.domain.aggregate.DeptGrade;

import java.util.Optional;

public interface DeptGradeRepository {

    DeptGrade save(DeptGrade deptGrade);

    Optional<DeptGrade> findByDeptGradeId(Long deptGradeId);
}
