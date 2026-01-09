package org.hit.hradar.domain.company.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "ceo_name", nullable = false, length = 50)
  private String ceoName;

  @Column(name = "address", nullable = false, length = 255)
  private String address;

  @Column(name = "phone", nullable = false, length = 30)
  private String phone;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "bis_no", nullable = false, length = 20)
  private String bisNo;

  @Column(name = "founded_date", nullable = false)
  private LocalDate foundedDate;

  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}