package org.hit.hradar.domain.leave.command.application.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.LeaveErrorCode;
import org.hit.hradar.domain.leave.command.application.dto.request.LeaveApplyRequest;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.hit.hradar.domain.leave.command.infrastructure.EmpLeaveJpaRepository;
import org.hit.hradar.domain.leave.command.infrastructure.LeaveUsageJpaRepository;
import org.hit.hradar.domain.leave.query.dto.response.LeaveGrantDto;
import org.hit.hradar.domain.leave.query.mapper.LeaveGrantMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveListMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveCommandService {

  private final EmpLeaveJpaRepository empLeaveJpaRepository;
  private final LeaveUsageJpaRepository leaveUsageJpaRepository;
  private final LeaveListMapper leaveListMapper;
  private final LeaveGrantMapper leaveGrantMapper;

  //휴가 도메인 데이터 저장(결재 이미 생성된 상태)
  public void applyLeave(
      Long docId,
      Long employeeId,
      LeaveApplyRequest request
  ) {

    //동일 docId 중복 저장 방지 (가장 먼저)
    if (empLeaveJpaRepository.existsByDocId(docId)) {
      throw new BusinessException(LeaveErrorCode.LEAVE_ALREADY_APPLIED);
    }
    //휴가 기간 중복 검증
    boolean overlap = leaveListMapper.existsOverlap(
        employeeId,
        request.getStartDate(),
        request.getEndDate()
    );

    if (overlap)  {
      throw new BusinessException(LeaveErrorCode.LEAVE_OVERLAP);
    }
    //연차 잔여 검증
    LeaveGrantDto grant = leaveGrantMapper.findByGrantId(request.getGrantId());
    if (grant == null) {
      throw new BusinessException(LeaveErrorCode.LEAVE_GRANT_NOT_FOUND);
    }

    if (grant.getRemainingDays() < request.getLeaveDays()) {
      throw new BusinessException(LeaveErrorCode.LEAVE_NOT_ENOUGH);
    }

    //emp_leave 저장(휴가 사실)
    EmpLeave leave  = EmpLeave.builder()
        .docId(docId)
        .empId(employeeId)
        .grantId(request.getGrantId())
        .leaveType(request.getLeaveType())
        .leaveUnitType(request.getLeaveUnitType())
        .reason(request.getReason())
        .startDate(request.getStartDate())
        .endDate(request.getEndDate())
        .leaveDays(request.getLeaveDays())
        .requestedAt(LocalDateTime.now())
        .isDeleted('N')
        .build();

    empLeaveJpaRepository.save(leave);
  }
}
