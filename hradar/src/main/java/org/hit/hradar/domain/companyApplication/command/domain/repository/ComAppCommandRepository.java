package org.hit.hradar.domain.companyApplication.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;

public interface ComAppCommandRepository {
  CompanyApplication save(CompanyApplication app);
  Optional<CompanyApplication> findByIdForUpdate(Long appId);
}