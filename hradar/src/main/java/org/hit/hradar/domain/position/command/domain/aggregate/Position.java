// src/main/java/org/hit/hradar/domain/org/Position.java
package org.hit.hradar.domain.position.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "positon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "position_id", nullable = false)
  private Long posId;

  @Column(name = "com_id", nullable = false, unique = true)
  private Long comId; // FK(COMPANY.com_id)

  @Column(name = "name", nullable = false, length = 50, unique = true)
  private String name;

  @Column(name = "rank", nullable = false)
  private Integer rank;
}
