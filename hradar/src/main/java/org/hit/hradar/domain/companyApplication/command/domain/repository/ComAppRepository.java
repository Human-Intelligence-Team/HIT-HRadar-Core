package org.hit.hradar.domain.companyApplication.command.domain.repository;

import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;

public interface ComAppRepository {
  CompanyApplication save(CompanyApplication app);
}