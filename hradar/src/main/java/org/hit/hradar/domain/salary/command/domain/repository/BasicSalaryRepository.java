package org.hit.hradar.domain.salary.command.domain.repository;


import java.util.List;
import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;

public interface BasicSalaryRepository {

  void saveAll(List<BasicSalary> basicSalaries);

  List<BasicSalary> findAllByDocId(Long docId);

  void deleteAllByDocId(Long docId);
}
