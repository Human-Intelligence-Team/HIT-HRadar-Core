package org.hit.hradar.domain.role.command.domain.aggregate;


import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "role")
public class Role extends BaseTimeEntity {

  @Id
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private String isDeleted; // Y/N
}