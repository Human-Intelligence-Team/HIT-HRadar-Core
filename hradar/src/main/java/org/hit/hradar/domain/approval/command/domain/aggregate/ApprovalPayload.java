package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval_payload")
@Getter
@NoArgsConstructor
public class ApprovalPayload {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payload_id")
  private Long payloadId;

  @Column(nullable = false)
  private Long docId;

  @Lob
  @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
  private String payload;

  private ApprovalPayload(Long docId, String payload) {
    this.docId = docId;
    this.payload = payload;
  }

  public static ApprovalPayload create(Long docId, String payloadJson)  {
    return new ApprovalPayload(docId, payloadJson);
  }
  public void update(String payloadJson) {
    this.payload = payloadJson;
  }

}
