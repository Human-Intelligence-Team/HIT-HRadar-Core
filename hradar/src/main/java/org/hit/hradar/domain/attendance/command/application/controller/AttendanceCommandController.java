package org.hit.hradar.domain.attendance.command.application.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.application.dto.response.AttendanceCheckResponse;
import org.hit.hradar.domain.attendance.command.application.service.AttendanceCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceCommandController {

  private final AttendanceCommandService attendanceCommandService;

  //회사 IP대역 기반 출퇴근 처리
  //사원의 출퇴근 버튼 클릭시 호출, (CHECK_IN/CHECK_OUT)자동 판단
  //회사 허용 IP대역이 아닐 경우 예외 발생
  @PostMapping("/check")
  public ResponseEntity<AttendanceCheckResponse> processAttendance(
     @CurrentUser AuthUser authUser,
     HttpServletRequest request
    ) {
    //클라이언트 IP 추출
    String clientIp = extractClientIp(request);

    //출퇴근 처리 위임
    AttendanceCheckResponse response =
        attendanceCommandService.processAttendance(
            authUser.userId(),
            authUser.companyId(),
            clientIp
        );

    return ResponseEntity.ok(response);
  }

  //클라리언트 ip추출 메서드
  //Gateway/proxy 환경을 고려하여 x-Forwarded-For 헤더 우선 사용
  private String extractClientIp(HttpServletRequest request) {
    String forwardedFor = request.getHeader("x-Forwarded-For");
    if (forwardedFor != null && !forwardedFor.isBlank()) {
      return forwardedFor.split(",")[0];
    }
    return request.getRemoteAddr();
  }
}
