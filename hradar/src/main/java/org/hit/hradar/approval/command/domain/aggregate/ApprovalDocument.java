package org.hit.hradar.approval.command.domain.aggregate;

import java.time.LocalDateTime;

public class ApprovalDocument {

  //결재 문서id
  private Integer approvalDocumentId;

  //부서 id
  private Integer deptId;

  //기안자 사원id
  private Integer drafterId;

  //문서 유형
  private String docType;

  //제목
  private String title;

  //본문
  private String content;

  //상태
  private String status;

  //제출일시
  private LocalDateTime submittedAt;

  //생성일시
  private LocalDateTime createdAt;

  //삭제일시
  private LocalDateTime deletedAt;

}
