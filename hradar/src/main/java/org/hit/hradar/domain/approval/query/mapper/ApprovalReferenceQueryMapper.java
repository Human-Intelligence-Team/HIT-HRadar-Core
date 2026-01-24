package org.hit.hradar.domain.approval.query.mapper;

import org.springframework.data.repository.query.Param;

public interface ApprovalReferenceQueryMapper {

  //기존 문서 참조자 하드 삭제(임시저장 수정시 사용)
  void deleteByDocId(@Param("docId") Long docId);

}
