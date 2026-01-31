package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "approval_document_type")
@Getter
@NoArgsConstructor
public class ApprovalDocumentType extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "type_id")
  private Long typeId;

  @Column(name = "company_id", nullable = false)
  private Long companyId;

  @Column(name = "doc_type", nullable = false)
  private String docType;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "is_active", nullable = false)
  private boolean active = true;

  public ApprovalDocumentType(Long companyId, String docType, String name, boolean active) {
    this.companyId = companyId;
    this.docType = docType;
    this.name = name;
    this.active = active;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void deactivate() {
    this.active = false;
  }
}