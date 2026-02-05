package org.hit.hradar.domain.competencyReport.query.dto;


import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentDTO {

  private Long contentId;
  private String title; // 콘텐츠명
  private Long type;// 유형
  private String typeCode; // 콘텐츠명
  private String typeName; // 콘텐츠명
  private Long level; // 난이도
  private String levelCode; // 난이도
  private String levelName; // 난이도
  private Integer learningTime; // 학습시간
  private String resourcePath; // 위치
  private String notes;
  private Character isDeleted; // 사용여부
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private String reason;
  // 태그
  private List<TagDTO> tags;

  public ContentDTO(Long contentId, String title, Long type, String typeCode,String typeName,Long level,
      String levelCode, String levelName,Integer learningTime, String resourcePath, Character isDeleted, String reason, List<TagDTO> tags) {

    this.contentId = contentId;
    this.title = title;
    this.type = type;
    this.typeCode = typeCode;
    this.typeName = typeName;
    this.level = level;
    this.levelCode = levelCode;
    this.levelName = levelName;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
    this.isDeleted = isDeleted;
    this.reason = reason;
    this.tags = tags;
  }

  public ContentDTO(Long contentId, String title, Long type, String typeCode,String typeName, Long level,
      String levelCode, String levelName, Integer learningTime, String resourcePath, String notes, Character isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.contentId = contentId;
    this.title = title;
    this.type = type;
    this.typeCode = typeCode;
    this.typeName = typeName;
    this.level = level;
    this.levelCode = levelCode;
    this.levelName = levelName;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
    this.notes = notes;
    this.isDeleted = isDeleted;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void addTags(List<TagDTO> tags) {
    if (tags == null || tags.isEmpty()) {
      return;
    }

    this.tags = tags;
  }


}
