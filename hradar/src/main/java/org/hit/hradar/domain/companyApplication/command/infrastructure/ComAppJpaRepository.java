package org.hit.hradar.domain.companyApplication.command.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface ComAppJpaRepository
    extends JpaRepository<CompanyApplication, Long>, ComAppCommandRepository {

  @Override
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select a from CompanyApplication a where a.appId = :appId")
  Optional<CompanyApplication> findByIdForUpdate(@Param("appId") Long appId);
}
