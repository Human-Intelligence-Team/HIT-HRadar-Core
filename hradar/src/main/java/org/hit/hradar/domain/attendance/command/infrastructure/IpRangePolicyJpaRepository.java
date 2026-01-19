package org.hit.hradar.domain.attendance.command.infrastructure;

import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;
import org.hit.hradar.domain.attendance.command.domain.repository.IpRangePolicyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRangePolicyJpaRepository extends JpaRepository<IpRangePolicy, Long>,
    IpRangePolicyRepository {


}
