package org.hit.hradar.domain.approval.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalMyDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalReferenceDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalRejectedDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalTaskResponse;

@Mapper
public interface ApprovalQueryMapper {
  // 내 문서함
  List<ApprovalMyDocumentResponse> selectMyDocuments(Long userId);

  // 결재 문서함 (결재 대상)
  List<ApprovalTaskResponse> selectApprovalTasks(Long userId);

  // 반려 문서함
  List<ApprovalRejectedDocumentResponse> selectRejectedDocuments(Long userId);

  // 참조 문서함
  List<ApprovalReferenceDocumentResponse> selectReferenceDocuments(Long userId);
}
