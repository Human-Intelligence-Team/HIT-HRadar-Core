package org.hit.hradar.domain.company.query.dto;

import java.time.LocalDate;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetailResponse {

  private Long companyId;
  private Long appId  ;

  private String comCode  ;
  private String ceoName;
  private String comEmail;
  private String companyName;
  private String bizNo;
  private String comTel;
  private String address;
  private LocalDate foundDate;

  private String status;
  private Character isDeleted;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
