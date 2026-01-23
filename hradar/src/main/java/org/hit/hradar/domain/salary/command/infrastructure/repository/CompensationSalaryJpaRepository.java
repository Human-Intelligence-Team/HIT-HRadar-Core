package org.hit.hradar.domain.salary.command.infrastructure.repository;


import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationSalary;
import org.hit.hradar.domain.salary.command.domain.repository.BasicSalaryRepository;
import org.hit.hradar.domain.salary.command.domain.repository.CompensationSalaryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationSalaryJpaRepository extends CompensationSalaryRepository, JpaRepository<CompensationSalary, Long> {



}
