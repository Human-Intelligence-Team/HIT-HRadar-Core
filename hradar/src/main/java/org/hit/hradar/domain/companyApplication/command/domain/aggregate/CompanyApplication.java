package org.hit.hradar.domain.companyApplication.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "company_application")
public class CompanyApplication extends BaseTimeEntity {

  @Id
  @Column(name = "application_id", nullable = false)
  private Long applicationId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "company_name", nullable = false, length = 255)
  private String companyName;

  @Column(name = "ceo_name", nullable = false, length = 255)
  private String ceoName;

  @Column(name = "phone", nullable = false, length = 30)
  private String phone;

  @Column(name = "bis_no", nullable = false, length = 20)
  private String bisNo;

  @Column(name = "address", nullable = false, length = 255)
  private String address;

  @Column(name = "status", nullable = false, length = 30)
  private CompanyStatus companyStatus;

  @Column(name = "reviewed_at")
  private LocalDateTime reviewedAt;

  @Column(name = "reject_reason", length = 255)
  private String rejectReason;
}

