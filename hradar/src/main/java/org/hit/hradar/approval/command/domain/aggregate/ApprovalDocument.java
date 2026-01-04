package org.hit.hradar.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

  /*부서 id
  @ManyToOne(fetch = FetchType.LAZY)
  @Column(name = "dept_id", nullable = false)
  private Long deptId;
  */

  /*기안자 사원id
  @ManyToOne(fetch = FetchType.LAZY)
  @Column(name = "drafter_id", nullable = false)
  private Integer drafterId;
  */

  //문서 유형
  @Enumerated(EnumType.STRING)
  @Column(name = "doc_type", nullable = false)
  private ApprovalDocumentType docType;

  //제목
  @Column(name = "title", nullable = false)
  private String title;

  //본문
  @Column(name = "content", nullable = false)
  private String content;

  //상태
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ApprovalStatus status;

  //제출일시
  @Column(name = "submitted_at")
  private LocalDateTime submittedAt;

  //삭제일시
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  /*생성일시
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
   */
}
