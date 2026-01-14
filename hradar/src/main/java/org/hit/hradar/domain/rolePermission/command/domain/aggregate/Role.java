package org.hit.hradar.domain.rolePermission.command.domain.aggregate;


import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;
@Entity
@Table(
    name = "role",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_ROLE_COMPANY_NAME", columnNames = {"com_id", "name"})
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Role extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Column(name = "com_id", nullable = false)
  private Long comId; // FK(COMPANY.com_id)

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "is_default", nullable = false, length = 1)
  private String isDefault; // 'Y'/'N'
}
