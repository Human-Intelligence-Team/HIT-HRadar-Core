package org.hit.hradar.domain.attendance.command.domain.policy;

import java.time.LocalDateTime;

public class IpRange {

  //회사ip
  private Integer ipId;

  //ip대역
  private String cidr;

  //관리자가 보는 용도(출퇴근용ip)
  private String description;

  //근무 지점
  private String locationName;

  //활성 여부
  private boolean isActive;

  //생성시각
  private LocalDateTime createdAt;

  //회사ID
  private Integer comId;
}
