package org.hit.hradar.domain.salary.command.infrastructure.repository;


import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.TagRepository;
import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;
import org.hit.hradar.domain.salary.command.domain.repository.BasicSalaryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicSalaryJpaRepository extends BasicSalaryRepository, JpaRepository<BasicSalary, Long> {



}
