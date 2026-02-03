package org.hit.hradar.domain.competencyReport.query.dto;

import lombok.Getter;

@Getter
public class ContentRowDTO {
  private Long contentId;
  private String title; // 콘텐츠명
  private String typeCode; // 콘텐츠명
  private String typeName; // 콘텐츠명
  private Long type;// 유형
  private Long level; // 난이도
  private String levelCode; // 난이도
  private String levelName; // 난이도
  private Integer learningTime; // 학습시간
  private String resourcePath; // 위치
  private Character isDeleted; // 사용여부

  // 태그
  private Long tagId;
  private String tagName;
}
