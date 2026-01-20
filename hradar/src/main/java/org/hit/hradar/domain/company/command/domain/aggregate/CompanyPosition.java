package org.hit.hradar.domain.company.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "company_position")
public class CompanyPosition extends BaseTimeEntity {

  @Id
  @Column(name = "com_position_id", nullable = false)
  private Long positionId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "rank", nullable = false)
  private Integer rank;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;
}
