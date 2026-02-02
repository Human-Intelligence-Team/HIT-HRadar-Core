package org.hit.hradar.domain.salary.command.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.List;
import org.hit.hradar.domain.salary.command.application.dto.SalaryDTO;
import org.hit.hradar.domain.salary.command.application.dto.request.CommonApprovalRequest;
import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SalaryCommandServiceTest {
  @InjectMocks
  private SalaryCommandService salaryCommandService;

  @Mock
  private BasicSalaryCommandService basicSalaryCommandService;

  @Mock
  private CompensationCommandService compensationCommandService;

  @Test
  @DisplayName("기본급 결재 등록 성공: 기존 데이터 삭제 후 기본급 서비스만 호출한다.")
  void createSalaryApproval_BasicSalary_Success() {
    // given
    Long empId = 500L;
    List<SalaryDTO> salaries = List.of(mock(SalaryDTO.class));

    // 11개 필드 생성자 호출 (순서 주의!)
    CommonApprovalRequest request = new CommonApprovalRequest(
        1L,                         // docId
        10L,                        // deptId
        empId,                      // writerId
        1L,                         // comId
        "BASIC_SALARY",             // approvalDocumentType
        "제목", "내용",              // title, content
        LocalDateTime.now(),        // submittedDate
        List.of(601L),              // referenceIds
        List.of(),                  // approvalLineSteps
        salaries                    // salaries
    );

    // 기존 데이터가 존재하여 삭제 로직을 타야 하는 상황 Mocking
    given(basicSalaryCommandService.getBasicSalariesByDocId(anyLong()))
        .willReturn(List.of(mock(BasicSalary.class)));

    // when
    salaryCommandService.createSalaryApproval(request, empId);

    // then
    then(basicSalaryCommandService).should(times(1)).getBasicSalariesByDocId(anyLong());
    then(basicSalaryCommandService).should(times(1)).deleteBasicSalariesByDocId(anyLong());
    then(basicSalaryCommandService).should(times(1)).createBasicSalaryApproval(anyLong(), anyList());

    // 변동보상 서비스는 절대 호출되면 안 됨
    then(compensationCommandService).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("변동보상 결재 등록 성공: 기존 데이터가 없으면 삭제 없이 변동보상 서비스만 호출한다.")
  void createSalaryApproval_Compensation_Success() {
    // given
    Long empId = 500L;
    List<SalaryDTO> salaries = List.of(mock(SalaryDTO.class));

    CommonApprovalRequest request = new CommonApprovalRequest(
        1L, 10L, empId, 1L,
        "COMPENSATION_SALARY",
        "제목", "내용", LocalDateTime.now(),
        List.of(), List.of(), salaries
    );

    // 기존 데이터가 없는 상황 Mocking
    given(compensationCommandService.getCompensationSalariesByDocId(anyLong()))
        .willReturn(List.of());

    // when
    salaryCommandService.createSalaryApproval(request, empId);

    // then
    then(compensationCommandService).should(times(1)).getCompensationSalariesByDocId(anyLong());
    then(compensationCommandService).should(never()).deleteCompensationSalariesByDocId(anyLong());
    then(compensationCommandService).should(times(1)).createCompensationSalaryApproval(anyLong(), anyList());

    // 기본급 서비스는 절대 호출되면 안 됨
    then(basicSalaryCommandService).shouldHaveNoInteractions();
  }
}