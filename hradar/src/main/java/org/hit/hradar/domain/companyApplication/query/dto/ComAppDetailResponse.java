package org.hit.hradar.domain.companyApplication.query.dto;

import java.time.LocalDateTime;
import lombok.*;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComAppDetailResponse {

  private Long applicationId;

  // company_application.company_name
  private String comName;

  // company_application.biz_no
  private String bizNo;

  // company_application.company_telephone
  private String comTel;

  // company_application.address
  private String address;

  // company_application.status
  private CompanyApplicationStatus status;

  // company_application.creater_*
  private String comAdminName;
  private String comAdminEmail;
  private String comAdminLoginId;

  // company_application.reviewed_*
  private LocalDateTime reviewedAt;
  private Long reviewedBy;

  // company_application.reject_reason
  private String rejectReason;
}
