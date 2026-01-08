package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "APPROVAL_REFERENCE")
@Getter
public class ApprovalReference extends BaseTimeEntity {

  //참조id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reference_id")
  private Long referenceId;

  //참조자 사원id
  @Column(name = "ref_emp_id", nullable = false)
  private Long refEmpId;

  //결재 문서id
  @Column(name = "approval_document_id", nullable = false)
  private Long approvalDocumentId;

  //생성자

  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted;
}
