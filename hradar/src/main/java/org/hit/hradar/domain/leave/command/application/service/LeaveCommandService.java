package org.hit.hradar.domain.leave.command.application.service;

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
    System.out.println("1 휴가 저장 ");

    //동일 docId 중복 저장 방지 (가장 먼저)
    if (empLeaveJpaRepository.existsByDocId(docId)) {
      throw new BusinessException(LeaveErrorCode.LEAVE_ALREADY_APPLIED);
    }
    System.out.println("2 휴가 저장 ");
    //휴가 기간 중복 검증
    boolean overlap = leaveListMapper.existsOverlap(
        employeeId,
        request.getStartDate(),
        request.getEndDate()
    );
    System.out.println("3 휴가 저장 ");
    if (overlap)  {
      throw new BusinessException(LeaveErrorCode.LEAVE_OVERLAP);
    }
    System.out.println("4 휴가 저장 ");
    //연차 잔여 검증
    LeaveGrantDto grant = leaveGrantMapper.findByGrantId(request.getGrantId());
    if (grant == null) {
      throw new BusinessException(LeaveErrorCode.LEAVE_GRANT_NOT_FOUND);
    }

    if (grant.getRemainingDays() < request.getLeaveDays()) {
      throw new BusinessException(LeaveErrorCode.LEAVE_NOT_ENOUGH);
    }

    //emp_leave 저장(휴가 사실)
    EmpLeave leave  = EmpLeave.create(
        docId,
        employeeId,
        request.getGrantId(),
        request.getLeaveType(),
        request.getLeaveUnitType(),
        request.getReason(),
        request.getStartDate(),
        request.getEndDate(),
        request.getLeaveDays()
    );
    System.out.println("5 휴가 저장 ");
    empLeaveJpaRepository.save(leave);
    System.out.println("6 휴가 저장 ");
  }
}
