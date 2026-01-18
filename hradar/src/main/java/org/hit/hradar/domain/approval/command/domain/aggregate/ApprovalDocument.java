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
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

@Entity
@Table(name = "approval_document")
@Getter
public class ApprovalDocument extends BaseTimeEntity {

  //결재 문서id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "doc_id")
  private Long docId;

  //부서 id
  @Column(name = "dept_id", nullable = false)
  private Long deptId;

  //기안자 사원id
  @Column(name = "writer_id", nullable = false)
  private Long writerId;

  //문서 유형
  @Enumerated(EnumType.STRING)
  @Column(name = "doc_type", nullable = false)
  private ApprovalDocumentType docType = ApprovalDocumentType.ATT_CORRECTION;

  // 업무 데이터 PK (vacationId, kpiId ...)
  @Column(name = "payload_id", nullable = false)
  private Long payloadId;

  //제목
  @Column(name = "title", nullable = false, length = 200)
  private String title;

  //본문
  @Column(name = "content", nullable = false)
  private String content;

  //상태(DRAFT / IN_PROGRESS / APPROVED / REJECTED)
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ApprovalStatus status = ApprovalStatus.DRAFT;

  //제출일시
  @Column(name = "submitted_at")
  private LocalDateTime submittedAt;

  //승인일시
  @Column(name = "approved_at")
  private LocalDateTime approvedAt;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  //결재 문서를 상신(제출)한다.
  //결재 문서는 반드시 DRAFT(임시저장) 상태에서만 상신 가능
  //이미 상신(IN_PROGRESS), 결재(APPROVED), 반려(REJECTED), 회수(WITHDRAW)는 상신 불가능
  //'임시저장' 버튼: 문서를 DRAFT(생성) 상태로 저장만 한다. (submit() 호출 X)
  // 제출' 버튼: 문서를 저장한 뒤 submit()을 호출한다.
  public void submit() {
    if (this.status != ApprovalStatus.DRAFT) {
      throw new BusinessException(
          ApprovalErrorCode.CANNOT_SUBMIT_NOT_DRAFT
      );
    }
    this.status = ApprovalStatus.IN_PROGRESS;
    this.submittedAt = LocalDateTime.now();
  }

   //결재 문서를 회수한다.(DRAFT(임시저장) 상태)
   //IN_PROGRESS(상신) 상태이면서, 아직 결재선에서 승인/반려가 한 건도 발생하지 않은 경우
   // 결재 문서 회수
   public void withdraw(Long actorId) {
     if (!this.writerId.equals(actorId)) {
       throw new BusinessException(
           ApprovalErrorCode.NOT_ALLOWED_APPROVER
       );
     }

     if (this.status == ApprovalStatus.APPROVED
         || this.status == ApprovalStatus.REJECTED) {
       throw new BusinessException(
           ApprovalErrorCode.CANNOT_WITHDRAW_AFTER_APPROVAL_STARTED
       );
     }

     this.status = ApprovalStatus.WITHDRAWN;
   }

  //결재 승인
  public void approve() {
    this.status = ApprovalStatus.APPROVED;
    this.approvedAt = LocalDateTime.now();
  }

  //결재 반려
  public void reject() {
    this.status = ApprovalStatus.REJECTED;
  }

  protected ApprovalDocument() {}

  private ApprovalDocument(
      Long writerId,
      ApprovalDocumentType docType,
      Long payloadId,
      String title,
      String content
  ) {
    this.writerId = writerId;
    this.docType = docType;
    this.payloadId = payloadId;
    this.title = title;
    this.content = content;
    this.status = ApprovalStatus.DRAFT;
  }

  public static ApprovalDocument createDraft(
      Long writerId,
      ApprovalDocumentType docType,
      Long payloadId,
      String title,
      String content
  ) {
    return new ApprovalDocument(
        writerId,
        docType,
        payloadId,
        title,
        content
    );
  }

}
