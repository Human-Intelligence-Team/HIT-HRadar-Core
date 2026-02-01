package org.hit.hradar.domain.approval.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.approval.query.dto.request.ApprovalAdminSearchRequest;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalAdminDocumentResponse;

@Mapper
public interface ApprovalAdminQueryMapper {

  List<ApprovalAdminDocumentResponse> selectAll(
      ApprovalAdminSearchRequest request
  );
}
