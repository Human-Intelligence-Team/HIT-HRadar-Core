package org.hit.hradar.domain.leave.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.LeaveErrorCode;
import org.hit.hradar.domain.leave.command.application.dto.request.LeaveApplyRequest;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveUsage;
import org.hit.hradar.domain.leave.command.domain.repository.EmpLeaveRepository;
import org.hit.hradar.domain.leave.command.domain.repository.LeaveUsageRepository;
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

  private final EmpLeaveRepository empLeaveRepository;
  private final LeaveUsageRepository leaveUsageRepository;
  private final LeaveListMapper leaveListMapper;
  private final LeaveGrantMapper leaveGrantMapper;

  //휴가 도메인 데이터 저장(결재 이미 생성된 상태)
  public void applyLeave(
      Long docId,
      Long employeeId,
      LeaveApplyRequest request
  ) {

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
    if (grant.getRemainingDays() < request.getLeaveDays())  {
      throw new BusinessException(LeaveErrorCode.LEAVE_NOT_ENOUGH);
    }

    //emp_leave 저장(휴가 사실)
    EmpLeave leave  = EmpLeave.create(
        docId,
        employeeId,
        request.getLeaveType(),
        request.getLeaveUnitType(),
        request.getReason(),
        request.getStartDate(),
        request.getEndDate(),
        request.getLeaveDays()
    );

    EmpLeave saved = empLeaveRepository.save(leave);

    //emp_usage 저장(차감 이력)
    LeaveUsage usage = LeaveUsage.create(
        leave.getLeaveId(),
        request.getGrantId(),
        saved.getLeaveDays(),
        request.getStartDate()
    );
    leaveUsageRepository.save(usage);
  }
}
