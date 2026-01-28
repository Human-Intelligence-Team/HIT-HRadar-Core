package org.hit.hradar.domain.companyApplication.query.dto;

import java.time.LocalDateTime;
import lombok.*;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComAppDetailResponse extends BaseTimeEntity {

  private Long applicationId;

  private String comName;
  private String ceoName;
  private String bizNo;
  private String comTel;
  private String address;

  private CompanyApplicationStatus status;

  private String comAdminName;
  private String comAdminEmail;
  private String comAdminLoginId;

  private LocalDateTime reviewedAt;
  private String rejectReason;

}
