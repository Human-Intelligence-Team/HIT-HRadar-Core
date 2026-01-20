package org.hit.hradar.domain.company.command.infrastructure;

import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository
    extends JpaRepository<Company, Long>, CompanyRepository {

  @Override
  boolean existsByComCode(String comCode);
}
