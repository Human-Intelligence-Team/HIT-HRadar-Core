package org.hit.hradar.domain.salary.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.command.application.dto.SalaryDTO;
import org.hit.hradar.domain.salary.command.application.dto.request.CommonApprovalRequest;
import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationSalary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryCommandService {

   private final BasicSalaryCommandService basicSalaryCommandService;
   private final CompensationCommandService compensationCommandService;
  /**
   * 결재 연봉 등록( 임시 저장 / 등록 )
   * @param commonApprovalRequest
   * @param empId
   */
  public void createSalaryApproval(CommonApprovalRequest commonApprovalRequest, Long empId) {

    // 결재문서, 참조자, 결재선 등록
    Long docId = 1L; // 문서 결과

    // 기본급/ 변동 보상 등록
    String approvalDocumentType = commonApprovalRequest.getApprovalDocumentType();

    // 기본급
    List<SalaryDTO> salaries = commonApprovalRequest.getSalaries();
    if (approvalDocumentType.equals("BASIC_SALARY")) {
      // 임시저장된 기본급 확인
      List<BasicSalary> basicSalaries = basicSalaryCommandService.getBasicSalariesByDocId(docId);
      if(!basicSalaries.isEmpty()){
         basicSalaryCommandService.deleteBasicSalariesByDocId(docId);
      }

      // 등록
      basicSalaryCommandService.createBasicSalaryApproval(docId, salaries);
    }

    // 변동 보상
    if (approvalDocumentType.equals("COMPENSATION_SALARY")) {

      // 임시저장된 변동 보상 확인
      List<CompensationSalary> compensationSalaries = compensationCommandService.getCompensationSalariesByDocId(docId);
      if (!compensationSalaries.isEmpty()){
        compensationCommandService.deleteCompensationSalariesByDocId(docId);
      }

      // 등록
      compensationCommandService.createCompensationSalaryApproval(docId, salaries);
    }

  }



}
