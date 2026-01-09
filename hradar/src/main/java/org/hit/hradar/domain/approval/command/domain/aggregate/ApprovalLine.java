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
@Table(name = "approval_line")
@Getter
public class ApprovalLine extends BaseTimeEntity {

  //결재선id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "approval_line_id")
  private Long approvalLineId;

  //결재문서id
  @Column(name = "approval_document_id", nullable = false)
  private Long approvalDocumentId;

  //결재선 이름
  @Column(name = "line_name", nullable = false, length = 100)
  private String lineName;

  //생성자

  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';
}
