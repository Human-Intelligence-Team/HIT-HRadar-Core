package org.hit.hradar.domain.competencyReport.query.dto;


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
  private Character isDeleted; // 사용여부

  // 태그
  private Long tagId;
  private String tagName;
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



}
