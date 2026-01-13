package org.hit.hradar.domain.attendance.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.IpPolicyErrorCode;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;
import org.hit.hradar.domain.attendance.command.domain.repository.IpRangePolicyRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IpRangePolicyCommandService {

  //JpaRepository를 상속받기 때문에 save, findById 사용 가능
  private final IpRangePolicyRepository ipRangePolicyRepository;

  //관리자 ip 정책 등록, 신규 정책은 insert(기존 정책 덮어쓰지 않음)
  public void register(Long comId,
      String cidr,
      String locationName,
      IpPolicyType type
  ){
    //IP 정책 엔티티 생성 (기본값 활성)
    IpRangePolicy policy =
        new IpRangePolicy(comId, cidr, locationName, type);

    //insert
    ipRangePolicyRepository.register(policy);
  }

  //관리자 ip 정책 일시적 비활성화, softDelete(출퇴근 판단에서 제외)
  @Transactional
  public void deactivate(Long ipId) {
    IpRangePolicy policy = ipRangePolicyRepository.findById(ipId)
        .orElseThrow(() ->
            new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND));

    //상태 변경(update)
    policy.deactivate();
  }

  //IP 정책 소프트 삭제, DB유지(관리자 화면에서 삭제)
  @Transactional
  public void softDelete(Long ipId) {
    IpRangePolicy policy = ipRangePolicyRepository.findById(ipId)
        .orElseThrow(() ->
            new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND));

    policy.softDelete();
  }
}
