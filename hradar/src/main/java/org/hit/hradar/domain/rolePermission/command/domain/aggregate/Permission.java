package org.hit.hradar.domain.rolePermission.command.domain.aggregate;

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
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "perm_id", nullable = false)
  private Long permId;

  @Column(name = "parent_perm_id")
  private Long parentPermId;

  @Column(name = "perm_key", nullable = false, length = 100, unique = true)
  private String permKey;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "perm_type", nullable = false, length = 20)
//  private PermissionType type;

  @Column(name = "route_path", length = 255)
  private String routePath;

  @Column(name = "description", length = 255)
  private String description;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;
}



