package org.hit.hradar.domain.competencyReport.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentRowDTO {
  private Long contentId;
  private Long competencyReportId;
  private String reason;
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

 public ContentRowDTO(Long contentId, Integer learningTime, Long level, String levelName, String resourcePath, String title, String typeName, Long tagId, String tagName, Character isDeleted) {
   this.contentId = contentId;
    this.learningTime = learningTime;
    this.level = level;
    this.levelName = levelName;
    this.resourcePath = resourcePath;
    this.title = title;
    this.typeName = typeName;
    this.tagId = tagId;
    this.tagName = tagName;
    this.isDeleted = isDeleted;
 }

 public ContentRowDTO(Long competencyReportId, String reason, Long contentId, String title, Long type, Long level, String levelCode, String levelName, Integer learningTime, String resourcePath, Long tagId, String tagName) {
   this.competencyReportId = competencyReportId;
   this.reason = reason;
   this.contentId = contentId;
   this.title = title;
   this.type = type;
   this.level = level;
   this.levelCode = levelCode;
   this.levelName = levelName;
   this.resourcePath = resourcePath;
   this.tagId = tagId;
   this.tagName = tagName;
 }

}
