package org.hit.hradar.domain.leave.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.query.dto.response.LeaveDetailDto;
import org.hit.hradar.domain.leave.query.dto.response.LeaveGrantDto;
import org.hit.hradar.domain.leave.query.dto.response.LeaveListDto;
import org.hit.hradar.domain.leave.query.mapper.LeaveDetailMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveGrantMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveListMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveQueryService {

  private final LeaveListMapper leaveListMapper;
  private final LeaveDetailMapper leaveDetailMapper;
  private final LeaveGrantMapper leaveGrantMapper;

  //사원 휴가 목록 조회
  public List<LeaveListDto> getMyLeaveList(Long employeeId)  {
    return leaveListMapper.findByEmpId(employeeId);
  }

  //휴가 상세 조회
  public LeaveDetailDto getLeaveDetail(Long leaveId) {
    return leaveDetailMapper.findDetail(leaveId);
  }

  //연차 지급/잔여 조회
  public LeaveGrantDto getLeaveGrant(Long grantId) {
    return leaveGrantMapper.findByGrantId(grantId);
  }




}
