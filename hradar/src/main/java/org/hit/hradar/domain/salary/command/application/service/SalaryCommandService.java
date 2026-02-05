package org.hit.hradar.domain.salary.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDraftCreateRequest;
import org.hit.hradar.domain.approval.command.application.service.provider.ApprovalProviderService;
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
  private final ApprovalProviderService approvalProviderService;
  /**
   * 결재 연봉 등록( 임시 저장 / 등록 )
   * @param request
   * @param empId
   */
  public void createSalaryApproval(CommonApprovalRequest request, Long empId, Long comId) {
    System.out.println("SalaryCommandService.createSalaryApproval");

    ApprovalDraftCreateRequest approvalDraft =
        new ApprovalDraftCreateRequest(
              request.getApprovalDocumentType()
            , request.getTitle()
            , null
            , request.getApproverIds()
            , request.getReferenceIds()
            , null
    );

    // 결재 문서 Id
    Long approvalDocId = approvalProviderService.save(
        request.getDocId(), empId, comId , approvalDraft, request.getApprovalSaveMode());

    System.out.println("SalaryCommandService.createSalaryApproval approvalDocId: " + approvalDocId);

    // 기본급/ 변동 보상 등록
    String approvalDocumentType = request.getApprovalDocumentType();
    System.out.println("SalaryCommandService.createSalaryApproval approvalDocumentType: " + approvalDocumentType);
    // 기본급
    List<SalaryDTO> salaries = request.getSalaries();
    if (approvalDocumentType.equals("BASIC_SALARY")) {

      System.out.println("SalaryCommandService.createSalaryApproval 기본급");

      // 임시저장된 기본급 확인
      List<BasicSalary> basicSalaries = basicSalaryCommandService.getBasicSalariesByDocId(approvalDocId);
      if(!basicSalaries.isEmpty()){
        basicSalaryCommandService.deleteBasicSalariesByDocId(approvalDocId);
      }

      // 기본급 저장
      basicSalaryCommandService.createBasicSalaryApproval(approvalDocId, salaries, request.getSalaryIncreaseType());
    }

    // 변동 보상
    List<SalaryDTO> compensations = request.getCompensationSalaries();
    if (approvalDocumentType.equals("COMPENSATION_SALARY")) {

      System.out.println("SalaryCommandService.createSalaryApproval 변동 보상 ");

      // 임시저장된 변동 보상 확인
      List<CompensationSalary> compensationSalaries = compensationCommandService.getCompensationSalariesByDocId(approvalDocId);
      if (!compensationSalaries.isEmpty()){
        compensationCommandService.deleteCompensationSalariesByDocId(approvalDocId);
      }

      // 변동보상 저장
      compensationCommandService.createCompensationSalaryApproval(approvalDocId, compensations);
    }

  }



}