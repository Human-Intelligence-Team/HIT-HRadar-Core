package org.hit.hradar.domain.approval.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDetailResponse;

@Mapper
public interface ApprovalDetailQueryMapper {

  ApprovalDetailResponse selectApprovalDetail(
      @Param("docId") Long docId,
      @Param("userId") Long userId
  );
}
