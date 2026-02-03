package org.hit.hradar.domain.companyApplication.command.domain.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComAppRepository {
  CompanyApplication save(CompanyApplication app);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select a from CompanyApplication a where a.appId = :appId")
  Optional<CompanyApplication> findByIdForUpdate(@Param("appId") Long appId);
}