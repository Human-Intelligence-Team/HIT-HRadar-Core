package org.hit.hradar.domain.approval.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalMyDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalReferenceDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalRejectedDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalTaskResponse;
import org.hit.hradar.domain.approval.query.mapper.ApprovalQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalQueryService {

  private final ApprovalQueryMapper approvalQueryMapper;

  //내 문서함 조회(로그인 사용자가 기안자인 문서)
  //사원/팀장/인사팀 공통
  public List<ApprovalMyDocumentResponse> findMyDocuments(Long employeeId) {
    return approvalQueryMapper.selectMyDocuments(employeeId);
  }

  //결재 문서함 조회(결재 단계가 PENDING)
  //로그인 사용자가 결재자/대리결재자
  public List<ApprovalTaskResponse> findApprovalTasks(Long employeeId) {
    return approvalQueryMapper.selectApprovalTasks(employeeId);
    }


  //반려 문서함 조회(로그인 사용자가 기안자)
  public List<ApprovalRejectedDocumentResponse> findRejectedDocuments(Long employeeId) {
    return approvalQueryMapper.selectRejectedDocuments(employeeId);
  }

  //참조 문서함 조회(로그인 사용자가 참조자)
  public List<ApprovalReferenceDocumentResponse> findReferenceDocuments(Long userId) {
    return approvalQueryMapper.selectReferenceDocuments(userId);

  }
}