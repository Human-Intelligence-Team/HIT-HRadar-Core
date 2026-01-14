package org.hit.hradar.domain.competencyReport.query.dto;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentType;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Level;

@Getter
public class ContentDTO {

  private Long contentId;
  private String title; // 콘텐츠명
  private ContentType type;// 유형
  private Level level; // 난이도
  private Integer learningTime; // 학습시간
  private String resourcePath; // 위치
  private String notes;
  private Character isDeleted; // 사용여부
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // 태그
  private List<TagDTO> tags;

  public ContentDTO(Long contentId, String title, ContentType type, Level level,
      Integer learningTime, String resourcePath, Character isDeleted, List<TagDTO> tags) {

    this.contentId = contentId;
    this.title = title;
    this.type = type;
    this.level = level;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
    this.isDeleted = isDeleted;
    this.tags = tags;
  }

  public ContentDTO(Long contentId, String title, ContentType type, Level level,
      Integer learningTime, String resourcePath, String notes, Character isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.contentId = contentId;
    this.title = title;
    this.type = type;
    this.level = level;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
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
