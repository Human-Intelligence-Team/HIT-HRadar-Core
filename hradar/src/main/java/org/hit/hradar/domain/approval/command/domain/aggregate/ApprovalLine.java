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
  @Column(name = "line_id")
  private Long lineId;

  //결재문서id
  @Column(name = "doc_id", nullable = false)
  private Long docId;

  //현재 진행 중 결재 단계
  @Column(name = "current_step", nullable = false, length = 100)
  private int currentStep = 1;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  // ApprovalLine
  public void increaseStep() {
    this.currentStep++;
  }
}
