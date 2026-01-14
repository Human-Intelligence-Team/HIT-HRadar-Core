package org.hit.hradar.domain.companyApplication.command.domain.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ComAppCommandRepository extends JpaRepository<CompanyApplication, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select a from CompanyApplication a where a.applicationId = :id")
  Optional<CompanyApplication> findByIdForUpdate(Long id);
}
