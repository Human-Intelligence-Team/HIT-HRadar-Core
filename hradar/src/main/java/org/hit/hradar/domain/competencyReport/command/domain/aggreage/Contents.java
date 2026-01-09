package org.hit.hradar.domain.competencyReport.command.domain.aggreage;

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
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentsRequest;
import org.hit.hradar.global.exception.BusinessException;

@Entity
@Getter
@Table(name="contents")
@NoArgsConstructor
public class Contents {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_id")
  private Long id;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private ContentType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "level")
  private Level level;

  @Column(name = "learning_time")
  private Integer learningTime;

  @Column(name = "resource_path", length = 3000)
  private String resourcePath;

  @Column(name = "notes", length = 2000)
  private String notes;

  @Column(name = "use_yn", length = 1)
  private UseYn useYn;

  public Contents(String title, ContentType type, Level level, Integer learningTime, String resourcePath, String notes, UseYn useYn) {
    this.title = title;
    this.type = type;
    this.level = level;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
    this.notes = notes;
    this.useYn = useYn;
  }

  // 등록
  public static Contents create(ContentsRequest request) {

    if (request.getTitle() == null) {
      throw new BusinessException(null);
    }

    if(request.getType() == null) {
      throw new BusinessException(null);
    }

    if(request.getLevel() == null) {
      throw new BusinessException(null);
    }

    UseYn useYn = request.getUseYn() != null
        ? request.getUseYn()
        : UseYn.Y;

    return new Contents(request.getTitle(), request.getType(), request.getLevel(), request.getLearningTime(), request.getResourcePath(), request.getNotes(), useYn);


  }


}
