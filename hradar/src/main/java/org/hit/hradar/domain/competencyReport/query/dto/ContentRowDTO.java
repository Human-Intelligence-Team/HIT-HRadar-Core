package org.hit.hradar.domain.competencyReport.query.dto;

import lombok.Getter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentType;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Level;
@Getter
public class ContentRowDTO {
  private Long contentId;
  private String title; // 콘텐츠명
  private ContentType type;// 유형
  private Level level; // 난이도
  private Integer learningTime; // 학습시간
  private String resourcePath; // 위치
  private Character isDeleted; // 사용여부

  // 태그
  private Long tagId;
  private String tagName;
}
