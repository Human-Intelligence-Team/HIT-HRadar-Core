package org.hit.hradar.domain.approval.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApprovalAccessQueryMapper {

  // 결재 문서 접근 권한 여부 확인
  boolean existsAccessibleUser(
      @Param("docId") Long docId,
      @Param("employeeId") Long employeeId
  );

}
