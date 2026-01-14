package org.hit.hradar.domain.company.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "company")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Company extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "owner_acc_id", nullable = false, unique = true)
  private Long accId;

  @Column(name = "company_code", nullable = false, length = 30, unique = true)
  private String comCode;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "ceo_name", nullable = false, length = 50)
  private String ceoName;

  @Column(name = "biz_no", nullable = false, length = 30, unique = true)
  private String bizNo;

  @Column(name = "address", nullable = false, length = 255)
  private String address;

  @Column(name = "company_telephone", nullable = false, length = 30)
  private String comTel;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "bis_no", nullable = false, length = 20)
  private String bisNo;

  @Column(name = "founded_date")
  private LocalDate foundedDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 15)
  private CompanyApplicationStatus status;

  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}