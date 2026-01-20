package org.hit.hradar.domain.company.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;

public interface CompanyRepository {
  Company save(Company company);
  Optional<Company> findById(Long comId);
  boolean existsByComCode(String comCode);
}
