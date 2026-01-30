package org.hit.hradar.domain.companyApplication.command.application.dto;

import java.time.LocalDateTime;
import lombok.*;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectComAppResponse {

  private Long appId;

  private CompanyApplicationStatus status;

  private String rejectReason;

  private LocalDateTime reviewedAt;

  private Long reviewerUserId;

}
