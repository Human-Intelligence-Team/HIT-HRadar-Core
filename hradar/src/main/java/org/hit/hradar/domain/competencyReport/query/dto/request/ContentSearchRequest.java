package org.hit.hradar.domain.competencyReport.query.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentSearchRequest {

  private String title; // 콘텐츠명
  private Long type;// 유형
  private Long level; // 난이도
  private Integer learningTime; // 학습시간
  private String tag; // 태그
  private Character isDeleted; // 사용여부

  private Long comId;

}
