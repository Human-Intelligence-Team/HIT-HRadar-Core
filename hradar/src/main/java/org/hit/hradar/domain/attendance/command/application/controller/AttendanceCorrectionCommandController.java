package org.hit.hradar.domain.attendance.command.application.controller;

import lombok.RequiredArgsConstructor;

import org.hit.hradar.domain.attendance.command.application.dto.request.AttendanceCorrectionRequest;
import org.hit.hradar.domain.attendance.command.application.service.AttendanceCorrectionCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance/corrections")
public class AttendanceCorrectionCommandController {

  private final AttendanceCorrectionCommandService attendanceCorrectionCommandService;

  //근태 정정 신청(사원)
  //사원이 근태 화면에서 정정 요청(결재 문서 생성, 상신됨)
  //REQUESTED

  //근태 정정 신청(사원)
  @PostMapping
  public void requestCorrections(
      @CurrentUser AuthUser authUser,
      @RequestBody AttendanceCorrectionRequest request
  ) {
    attendanceCorrectionCommandService.requestCorrection(
        authUser.userId(),    //요청자(empId)
        request
    );
  }

  //근태 정정 승인 결과 반영
  //결재 시스템에서 "승인 완료" 후 호출(근태에서 결정 x)
  //승인시 log에 자동 반영
  @PatchMapping("{correctionId}/approved")
  public void applyApprovedCorrection(
      @PathVariable Long correctionId,
      @CurrentUser AuthUser authUser
  ) {
    attendanceCorrectionCommandService.applyApprovedCorrection(
        correctionId,
        authUser.userId()     //결정자(empId, 로그용)
    );
  }

  //근태 정정 반려 결과 반영
  //근태 데이터 변경 없음(반려 사유 기록)
  @PatchMapping("{correctionId}/rejected")
  public void applyRejectedCorrection(
      @PathVariable Long correctionId,
      @CurrentUser AuthUser authUser,
      @RequestParam String rejectReason
  ) {
    attendanceCorrectionCommandService.applyRejectedCorrection(
        correctionId,
        authUser.userId(),
        rejectReason
    );
  }
}
