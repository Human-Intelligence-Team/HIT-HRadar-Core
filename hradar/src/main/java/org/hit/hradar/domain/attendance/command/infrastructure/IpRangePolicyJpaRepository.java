package org.hit.hradar.domain.attendance.command.infrastructure;

import java.util.List;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpRangePolicyJpaRepository
    extends JpaRepository<IpRangePolicy, Long> {

  List<IpRangePolicy> findByComId(Long comId);

  List<IpRangePolicy> findByComIdAndIsActiveTrueAndIsDeletedFalse(Long comId);

  List<IpRangePolicy>
  findByComIdAndIpPolicyTypeAndIsActiveTrueAndIsDeletedFalse(
      Long comId,
      IpPolicyType ipPolicyType
  );
}
