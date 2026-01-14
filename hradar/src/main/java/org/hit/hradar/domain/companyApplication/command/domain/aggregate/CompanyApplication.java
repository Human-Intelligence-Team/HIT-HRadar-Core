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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "application_id", nullable = false)
  private Long applicationId;

  @Column(name = "company_name", nullable = false, length = 200)
  private String comName;

  @Column(name = "ceo_name", nullable = false, length = 100)
  private String ceoName;

  @Column(name = "biz_no", nullable = false, length = 30)
  private String bizNo;

  @Column(name = "company_telephone", nullable = false, length = 30)
  private String comTel;

  @Column(name = "address", nullable = false, length = 255)
  private String address;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 15)
  private CompanyApplicationStatus status;

  // 최고관리자 후보
  @Column(name = "company_admin_name", nullable = false, length = 50)
  private String comAdminName;

  @Column(name = "company_admin_email", length = 30, nullable = false)
  private String comAdminEmail;

  @Column(name = "company_admin_login_id", length = 100, nullable = false, unique = true)
  private String comAdminLoginId;

  @Column(name = "reviewed_at")
  private LocalDateTime reviewedAt;

  @Column(name = "reject_reason", length = 500)
  private String rejectReason;
}

