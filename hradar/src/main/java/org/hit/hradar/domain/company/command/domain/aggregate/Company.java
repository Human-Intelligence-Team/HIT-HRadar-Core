package org.hit.hradar.domain.company.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Company extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_id", nullable = false)
  private Long companyId;

  @Column(name = "application_id", nullable = false)
  private Long appId;

  @Column(name = "company_code", nullable = false, length = 30, unique = true)
  private String comCode;

  @Column(name = "company_name", nullable = false, length = 100)
  private String comName;

  @Column(name = "ceo_name", length = 50)
  private String ceoName;

  @Column(name = "company_email", length = 100)
  private String comEmail;

  @Column(name = "biz_no", nullable = false, length = 30, unique = true)
  private String bizNo;

  @Column(name = "address", nullable = false, length = 255)
  private String address;

  @Column(name = "company_telephone", length = 30)
  private String comTel;

  @Column(name = "founded_date")
  private LocalDate foundedDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 15)
  private CompanyApplicationStatus status;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

}