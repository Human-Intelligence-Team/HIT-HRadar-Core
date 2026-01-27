package org.hit.hradar.domain.approval.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalCommentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDetailResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalHistoryResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalLineStepResponse;


@Mapper
public interface ApprovalDetailQueryMapper {


  //결재 문서 기본 정보 조회 , 접근 권한 체크용 (docId + userId)
  ApprovalDetailResponse selectApprovalDetail(
      @Param("docId") Long docId,
      @Param("userId") Long userId
  );

  // [관리자용] 결재 문서 상세 조회
  ApprovalDetailResponse selectApprovalDetailByAdmin(
      @Param("docId") Long docId
  );

  //결재선 조회 (단계 순서대로)
  List<ApprovalLineStepResponse> selectApprovalSteps(
      @Param("docId") Long docId
  );

  //결재 히스토리 조회(SUBMITTED / APPROVED / REJECTED / WITHDRAW)
  List<ApprovalHistoryResponse> selectApprovalHistories(
      @Param("docId") Long docId
  );

  //댓글 조회
  List<ApprovalCommentResponse> selectApprovalComments(
      @Param("docId") Long docId
  );
}
