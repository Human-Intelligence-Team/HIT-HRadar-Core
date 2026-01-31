package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalAdminDocumentResponse {

  private Long docId;
  private String title;
  private String docType;
  private String status;
  private Long deptId;
  private String deptName;
  private Long writerId;
  private String writerName;
  private LocalDateTime submittedAt;

}
