package org.hit.hradar.domain.attendance.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "ip_range_policy")
@NoArgsConstructor
@Getter
public class IpRangePolicy extends BaseTimeEntity {

  //회사 정책ip
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
  @Enumerated(EnumType.STRING)
  @Column(name = "ip_policy_type", nullable = false)
  private IpPolicyType ipPolicyType = IpPolicyType.ATTENDANCE;

  //활성 여부
  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  //일시 비활성화
  public void deactivate() {
    this.isActive = false;
  }

  //ip 소프트 삭제(호출시 삭제)
  public void softDelete() {
    this.isDeleted = 'Y';
  }

  public IpRangePolicy(Long comId, String cidr, String locationName, IpPolicyType ipPolicyType) {
    this.comId = comId;
    this.cidr = cidr;
    this.locationName = locationName;
    this.ipPolicyType = ipPolicyType;
    this.isActive = true;
  }
}
