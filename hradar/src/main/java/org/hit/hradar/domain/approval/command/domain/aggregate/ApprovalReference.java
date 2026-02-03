package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

//결재 프로세스에는 관여하지 않음 (승인/반려 불가)
//문서를 "열람"할 수 있는 사용자 목록 관리 목적
@Entity
@Table(name = "approval_reference")
@Getter
public class ApprovalReference extends BaseTimeEntity {

  //참조 레코드 ID (PK)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reference_id")
  private Long Id;

  //참조자 사원 ID, 결재 권한은 없음
  @Column(name = "ref_emp_id")
  private Long refEmpId;

  //결재 문서 ID (FK)
  //approval_document.doc_id
  @Column(name = "doc_id", nullable = false)
  private Long docId;

  //논리 삭제 여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  protected ApprovalReference() {
  }

  //참조자 단건 생성자
  public ApprovalReference(Long docId, Long referenceId) {
    this.docId = docId;
    this.refEmpId = referenceId;
    this.isDeleted = 'N';
  }

  //참조자 목록 일괄 생성, 결재 문서 생성 시 함께 호출
  public static List<ApprovalReference> createAll(
      Long docId,
      List<Long> referenceIds
  ) {
    return referenceIds.stream()
        .map(refId -> new ApprovalReference(docId, refId))
        .toList();
  }
}
