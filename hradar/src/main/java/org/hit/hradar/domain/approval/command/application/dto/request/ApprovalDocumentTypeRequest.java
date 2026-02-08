package org.hit.hradar.domain.approval.command.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDocumentTypeRequest {

  private String docType;
  private String name;
  private Boolean active;
  private ApprovalAttendanceCategory attendanceCategory;
  private Integer overtimeMinutes;
}
