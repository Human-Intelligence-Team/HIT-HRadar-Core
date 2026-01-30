package org.hit.hradar.domain.leave.command.infrastructure;


import java.util.Optional;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeavePolicy;
import org.hit.hradar.domain.leave.command.domain.repository.LeavePolicyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeavePolicyJpaRepository
    extends JpaRepository<LeavePolicy, Long>, LeavePolicyRepository {

  Optional<LeavePolicy> findByCompanyIdAndTypeCodeAndUnitCodeAndIsActive(
      Long companyId,
      String typeCode,
      String unitCode,
      Character isActive
  );

  boolean existsByCompanyIdAndTypeCodeAndUnitCode(
      Long companyId,
      String typeCode,
      String unitCode
  );
}