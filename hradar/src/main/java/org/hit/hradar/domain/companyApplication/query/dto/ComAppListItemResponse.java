package org.hit.hradar.domain.companyApplication.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@Setter
@NoArgsConstructor
public class ComAppListItemResponse extends BaseTimeEntity {

  private Long applicationId;
  private String comName;
  private String bizNo;
  private String comAdminLoginId;
  private String status;
}
