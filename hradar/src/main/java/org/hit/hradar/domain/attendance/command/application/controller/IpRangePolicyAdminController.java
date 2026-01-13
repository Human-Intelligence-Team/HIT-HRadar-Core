package org.hit.hradar.domain.attendance.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.application.service.IpRangePolicyCommandService;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/ip-policies")
public class IpRangePolicyAdminController {

  private final IpRangePolicyCommandService ipRangePolicyCommandService;

  //관리자 IP 정책 등록(기본 상태는 활성), 신규 IP 대역을 정책으로 추가
  @PostMapping
  public void register(
      @RequestParam Long comId,
      @RequestParam String cidr,
      @RequestParam String locationName,
      @RequestParam IpPolicyType type
  ){
    ipRangePolicyCommandService.register(comId, cidr, locationName, type);
  }

  //관리자 ip 정책 일시적 비활성화(출퇴근 판단에서 제외됨)
  @PatchMapping("/{ipId}/deactivate")
  public void deactivate(@PathVariable Long ipId) {
    ipRangePolicyCommandService.deactivate(ipId);
  }

  //관리자 IP정책 소프트삭제(데이터 유지)
  @DeleteMapping("/{ipId}")
  public void delete(@PathVariable Long ipId) {
    ipRangePolicyCommandService.softDelete(ipId);
  }
}
