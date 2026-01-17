package org.hit.hradar.domain.attendance.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.IpPolicyErrorCode;
import org.hit.hradar.domain.attendance.command.application.dto.request.ChangeIpPolicyRequest;
import org.hit.hradar.domain.attendance.command.application.dto.request.RegisterIpPolicyRequest;
import org.hit.hradar.domain.attendance.command.application.dto.request.UpdateIpPolicyRequest;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;

import org.hit.hradar.domain.attendance.command.domain.repository.IpRangePolicyRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IpRangePolicyCommandService {

  private final IpRangePolicyRepository ipRangePolicyRepository;

  //관리자 ip 정책 등록, 신규 정책은 insert(기존 정책 덮어쓰지 않음)
  public void registerIpPolicy(RegisterIpPolicyRequest request) {
    //IP 정책 엔티티 생성 (기본값 활성)
    IpRangePolicy policy = new IpRangePolicy(
        request.getComId(),
        request.getCidr(),
        request.getLocationName(),
        request.getIpPolicyType()
  );
    //insert
    ipRangePolicyRepository.save(policy);
  }

  //관리자 ip 정책 수정(ip주소, 장소, 용도 수정)
  @Transactional
  public void updateIpPolicy(Long ipId, UpdateIpPolicyRequest request) {
      IpRangePolicy policy = ipRangePolicyRepository.findById(ipId)
          .orElseThrow(() ->
              new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND));

      policy.update(
          request.getCidr(),
          request.getLocationName(),
          request.getIpPolicyType()
      );
    }

  //관리자 ip 정책 일시적 활성/비활성,
  @Transactional
  public void changeIpPolicy(Long ipId, ChangeIpPolicyRequest request) {
    IpRangePolicy policy = ipRangePolicyRepository.findById(ipId)
        .orElseThrow(() ->
            new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND));

    //상태 변경(update)
    if (request.isActive()) {
      policy.isActive();
    } else {
      policy.deactivate();
    }
  }

  //IP 정책 소프트 삭제, DB유지(관리자 화면에서 삭제)
  @Transactional
  public void softDeleteIpPolicy(Long ipId) {
    IpRangePolicy policy = ipRangePolicyRepository.findById(ipId)
        .orElseThrow(() ->
            new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND));

    policy.softDelete();
  }
}
