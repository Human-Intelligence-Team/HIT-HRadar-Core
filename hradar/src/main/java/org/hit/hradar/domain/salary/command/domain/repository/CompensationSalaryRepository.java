package org.hit.hradar.domain.salary.command.domain.repository;

import java.util.List;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationSalary;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationSalaryRepository {

   void saveAllWithPolicy(List<CompensationSalary> compensationSalaries);

  List<CompensationSalary> findAllByDocId(Long docId);

  void deleteAllByDocId(Long docId);

}
