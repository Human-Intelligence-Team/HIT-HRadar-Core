package org.hit.hradar.domain.positions.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "company_position",
uniqueConstraints = @UniqueConstraint(name="UK_POSITION_COMPANY_NAME", columnNames={"company_id","name"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Positions extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "position_id", nullable = false)
  private Long positionId;

  @Column(name = "company_id", nullable = false)
  private Long comId;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "rank", nullable = false)
  private Integer rank;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @Builder
  public Positions(Long positionId, Long comId, String name, Integer rank) {
    this.positionId = positionId;
    this.comId = comId;
    this.name = name;
    this.rank = rank;
    this.isDeleted = 'N';
  }

  public void updatePosition(String name, Integer rank) {
    this.name = name;
    this.rank = rank;
  }

  public void isDeleted() {
    this.isDeleted = 'Y';
  }
}
