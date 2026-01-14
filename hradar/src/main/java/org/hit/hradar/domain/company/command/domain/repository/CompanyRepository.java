package org.hit.hradar.domain.company.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
  Optional<Company> findByComCode(String comCode);
  boolean existsByComCode(String companyCode);

}