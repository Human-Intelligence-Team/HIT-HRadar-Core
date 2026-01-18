package org.hit.hradar.domain.approval.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDetailResponse;
import org.hit.hradar.domain.approval.query.mapper.ApprovalAccessQueryMapper;
import org.hit.hradar.domain.approval.query.mapper.ApprovalDetailQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalDetailQueryService {

  // 결재 문서 상세 조회용 Mapper
  private final ApprovalDetailQueryMapper approvalDetailQueryMapper;
  // 결재 문서 접근 권한 체크용 Mapper
  private final ApprovalAccessQueryMapper approvalAccessQueryMapper;

  //결재 문서 상세 조회
  //문서 기본 정보
  //결재선(단계/결재자/상태)
  //결재 히스토리
  //댓글 목록
  //접근 가능자:기안자/결재자/대리결재자/참조자

  public ApprovalDetailResponse findDetail(Long docId, Long userId) {
    //접근 권한 체크
    if (!approvalAccessQueryMapper.existsAccessibleUser(docId, userId)) {
      throw new BusinessException(ApprovalErrorCode.NOT_ALLOWED_APPROVER);
    }
    //상세 조회
    return approvalDetailQueryMapper.selectApprovalDetail(docId, userId);
  }

}
