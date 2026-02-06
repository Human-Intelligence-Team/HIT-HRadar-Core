package org.hit.hradar.domain.leave.command.application.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.LeaveErrorCode;
import org.hit.hradar.domain.leave.command.application.dto.request.LeaveApplyRequest;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveUsage;
import org.hit.hradar.domain.leave.command.infrastructure.EmpLeaveJpaRepository;
import org.hit.hradar.domain.leave.command.infrastructure.LeaveUsageJpaRepository;
import org.hit.hradar.domain.leave.query.dto.response.LeaveGrantDto;
import org.hit.hradar.domain.leave.query.mapper.LeaveGrantMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveListMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveUsageMapper;
import org.hit.hradar.domain.leave.command.infrastructure.LeavePolicyJpaRepository; // Added import
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
  private final LeaveUsageMapper leaveUsageMapper;
  private final LeavePolicyJpaRepository leavePolicyJpaRepository; // Injected


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
        request.getFromDate(),
        request.getToDate()
    );

    if (overlap)  {
      throw new BusinessException(LeaveErrorCode.LEAVE_OVERLAP);
    }
    //연차 잔여 검증
    LeaveGrantDto grant = leaveGrantMapper.findByGrantId(request.getLeaveGrantId());
    if (grant == null) {
      throw new BusinessException(LeaveErrorCode.LEAVE_GRANT_NOT_FOUND);
    }

    if (grant.getRemainingDays() < request.getDays()) {
      throw new BusinessException(LeaveErrorCode.LEAVE_NOT_ENOUGH);
    }

    // Leave Type Name Resolution
    String leaveTypeName = leavePolicyJpaRepository.findById(request.getLeaveTypeId())
            .map(p -> p.getTypeName())
            .orElse("연차"); // Default fallback or throw exception

    //emp_leave 저장(휴가 사실)
    EmpLeave leave  = EmpLeave.builder()
        .docId(docId)
        .empId(employeeId)
        .grantId(request.getLeaveGrantId())
        .leaveType(leaveTypeName) // Resolved Name
        .leaveUnitType(request.getLeaveUnitType())
        .reason(request.getReason())
        .startDate(request.getFromDate())
        .endDate(request.getToDate())
        .leaveDays(request.getDays())
        .requestedAt(LocalDateTime.now())
        .isDeleted('N')
        .build();

    empLeaveJpaRepository.save(leave);

    leaveUsageJpaRepository.save(
        LeaveUsage.create(
            leave.getLeaveId(),
            request.getLeaveGrantId(),
            request.getDays(),
            request.getFromDate()
        )
    );

// leave_grant remaining_days 차감
    leaveUsageMapper.decreaseRemainingDays(
        request.getLeaveGrantId(),
        request.getDays()
    );
  }
}
