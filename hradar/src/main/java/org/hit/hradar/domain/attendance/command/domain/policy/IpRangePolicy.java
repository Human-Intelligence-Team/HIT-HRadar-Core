package org.hit.hradar.domain.attendance.command.domain.policy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "ip_range_policy")
@Getter
public class IpRangePolicy extends BaseTimeEntity {

  //회사ip
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ip_id")
  private Long ipId;

  //회사ID
  @Column(name = "com_id", nullable = false)
  private Long comId;

  //ip대역
  @Column(name = "cidr", nullable = false)
  private String cidr;

  //근무 지점
  @Column(name = "location_name", nullable = false)
  private String locationName;

  //IP 사용유형
  @Column(name = "ip_type")
  private String ipType;

  //활성 여부
  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';
}
