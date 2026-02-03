package org.hit.hradar.domain.attendance.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.application.dto.request.ChangeIpPolicyRequest;
import org.hit.hradar.domain.attendance.command.application.dto.request.RegisterIpPolicyRequest;
import org.hit.hradar.domain.attendance.command.application.dto.request.UpdateIpPolicyRequest;
import org.hit.hradar.domain.attendance.command.application.service.IpRangePolicyCommandService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attendance IP Policy Admin", description = "관리자용 IP 정책 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance/ip-policies")
public class IpRangePolicyAdminController {

  private final IpRangePolicyCommandService ipRangePolicyCommandService;

  // 관리자 IP 정책 등록(기본 상태는 활성), 신규 IP 대역을 정책으로 추가
  @Operation(summary = "IP 정책 등록", description = "출퇴근이 가능한 새로운 IP 대역 정책을 등록합니다.")
  @PostMapping
  public void registerIpPolicy(
      @RequestBody RegisterIpPolicyRequest request) {
    ipRangePolicyCommandService.registerIpPolicy(request);
  }

  // 관리자 IP 정책 수정(CIDR, location, type)
  @Operation(summary = "IP 정책 수정", description = "기존 IP 정책 정보(CIDR, 위치 등)를 수정합니다.")
  @PatchMapping("/{ipId}")
  public void updateIpPolicy(
      @PathVariable Long ipId,
      @RequestBody UpdateIpPolicyRequest request) {
    ipRangePolicyCommandService.updateIpPolicy(ipId, request);
  }

  // 관리자 ip 정책 일시적 활성/비활성(출퇴근 판단에서 제외됨)
  @Operation(summary = "IP 정책 상태 변경", description = "IP 정책의 활성/비활성 상태를 변경합니다.")
  @PatchMapping("/{ipId}/status")
  public void changeIpPolicy(
      @PathVariable Long ipId,
      @RequestBody ChangeIpPolicyRequest request) {
    ipRangePolicyCommandService.changeIpPolicy(ipId, request);
  }

  // 관리자 IP정책 소프트삭제(데이터 유지)
  @Operation(summary = "IP 정책 삭제", description = "IP 정책을 삭제(소프트 삭제)합니다.")
  @DeleteMapping("/{ipId}")
  public void deleteIpPolicy(
      @PathVariable Long ipId) {
    ipRangePolicyCommandService.softDeleteIpPolicy(ipId);

  }
}
