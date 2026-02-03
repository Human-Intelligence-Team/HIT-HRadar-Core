package org.hit.hradar.domain.attendance.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.AttendanceErrorCode;
import org.hit.hradar.domain.attendance.command.application.dto.request.WorkPlanCreateRequest;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkPlan;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkPlanJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkPlanCommandService {

  private final AttendanceWorkPlanJpaRepository attendanceWorkPlanJpaRepository;

  public void createWorkPlan(
      Long empId,
      Long docId,
      WorkPlanCreateRequest request
  ) {
    AttendanceWorkPlan plan = AttendanceWorkPlan.create(
        empId,
        docId,
        request.getWorkType(),
        request.getLocation(),
        request.getStartAt(),
        request.getEndAt(),
        request.getOvertimeMinutes()
    );

    if (request.getStartAt().isAfter(request.getEndAt())) {
      throw new BusinessException(AttendanceErrorCode.INVALID_WORK_TIME);
    }

    attendanceWorkPlanJpaRepository.save(plan);
  }
}
