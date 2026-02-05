package org.hit.hradar.domain.salary.command.domain.repository;


import java.util.List;
import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicSalaryRepository {

  void saveAllWithPolicy(List<BasicSalary> basicSalaries);

  List<BasicSalary> findAllByDocId(Long docId);

  void deleteAllByDocId(Long docId);

}
