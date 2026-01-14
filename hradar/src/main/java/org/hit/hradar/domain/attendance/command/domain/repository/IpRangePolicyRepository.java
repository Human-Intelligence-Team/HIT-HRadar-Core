package org.hit.hradar.domain.attendance.command.domain.repository;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRangePolicyRepository extends JpaRepository<IpRangePolicy, Long> {

  // 관리자 ip전체 목록
  List<IpRangePolicy> findByComId(Long comId);

  // 활성 ip목록(전체 타입)
  List<IpRangePolicy> findByComIdAndIsActiveTrue(Long comId);

  // 출퇴근용 목록(ATTENDANCE)
  List<IpRangePolicy> findByIpPolicyTypeAndIsActiveTrue(IpPolicyType ipPolicyType);

  // 관리자: IP 정책 등록
  default void register(IpRangePolicy policy) {
    save(policy);
  }

  // 관리자: IP 정책 단건 조회 (비활성/삭제용)
  Optional<IpRangePolicy> findById(Long ipId);


}
