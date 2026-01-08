package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "APPROVAL_DOCUMENT")
@Getter
public class ApprovalDocument extends BaseTimeEntity {

  //결재 문서id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "approval_document_id")
  private Long approvalDocumentId;

  //부서 id
  @Column(name = "dept_id", nullable = false)
  private Long deptId;

  //기안자 사원id
  @Column(name = "drafter_id", nullable = false)
  private Long drafterId;

  //문서 유형
  @Column(name = "doc_type", nullable = false, length = 50)
  private String  docType;

  //제목
  @Column(name = "title", nullable = false, length = 200)
  private String title;

  //본문
  @Column(name = "content", nullable = false)
  private String content;

  //상태
  @Column(name = "status", nullable = false, length = 50)
  private String status;

  //제출일시
  @Column(name = "submitted_at")
  private LocalDateTime submittedAt;

  //생성자

  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted;

}
