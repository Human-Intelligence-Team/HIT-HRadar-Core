package org.hit.hradar.domain.leave.command.infrastructure;


import java.util.List;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeavePolicy;
import org.hit.hradar.domain.leave.command.domain.repository.LeavePolicyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeavePolicyJpaRepository
    extends JpaRepository<LeavePolicy, Long>, LeavePolicyRepository {


  boolean existsByCompanyIdAndTypeName(
      Long companyId,
      String typeName
  );

  List<LeavePolicy> findByCompanyIdAndIsDeleted(Long companyId, Character isDeleted);

}