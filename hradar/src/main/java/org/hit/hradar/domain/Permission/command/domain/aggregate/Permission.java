package org.hit.hradar.domain.Permission.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "permission")
public class Permission extends BaseTimeEntity {

  @Id
  @Column(name = "perm_id", nullable = false)
  private Long permId;

  @Column(name = "parent_perm_id", nullable = false)
  private Long parentPermId;

  @Column(name = "perm_code", nullable = false, length = 100)
  private String permCode;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "route_path", length = 255)
  private String routePath;

  @Column(name = "description", length = 255)
  private String description;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private String isDeleted = "N";
}