package org.hit.hradar.domain.salary.command.domain.repository;

import java.util.List;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationSalary;

public interface CompensationSalaryRepository {

  void saveAll(List<CompensationSalary> compensationSalaries);

  List<CompensationSalary> findAllByDocId(Long docId);

  void deleteAllByDocId(Long docId);
}
