package org.hit.hradar.domain.competencyReport.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentUpdateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentCreateRequest;
import org.hit.hradar.domain.competencyReport.competencyReportErrorCode.CompetencyReportErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

@Entity
@Getter
@Table(name="content")
@NoArgsConstructor
public class Content extends BaseTimeEntity {

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
  @Column(name = "level", nullable = false )
  private Level level;

  @Column(name = "learning_time")
  private Integer learningTime;

  @Column(name = "resource_path", length = 3000)
  private String resourcePath;

  @Column(name = "notes", length = 2000)
  private String notes;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @PrePersist
  public void prePersist() {
    if (this.isDeleted == null) {
      this.isDeleted = 'N';
    }
  }

  public Content(String title, ContentType type, Level level, Integer learningTime, String resourcePath, String notes) {
    this.title = title;
    this.type = type;
    this.level = level;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
    this.notes = notes;
  }

  // 유효성
  private static void validate(String title, ContentType type, Level level) {
    if (title == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_TITLE_REQUIRED);
    }

    if(type == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_TYPE_REQUIRED);
    }

    if(level == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_LEVEL_REQUIRED);
    }
  }

  // 등록
  public static Content create(ContentCreateRequest request) {

    // validation
    validate(request.getTitle(), request.getType(), request.getLevel());

    String normalizedTitle = request.getTitle().trim();
    String normalizedResourcePath = request.getResourcePath().trim();
    String normalizedNotes = request.getNotes().trim();

    return new Content(normalizedTitle
                    , request.getType()
                    , request.getLevel()
                    , request.getLearningTime()
                    , normalizedResourcePath
                    , normalizedNotes);

  }

  // 수정
  public void update(ContentUpdateRequest request) {

    // validation
    validate(request.getTitle(), request.getType(), request.getLevel());

    // trim
    String normalizedTitle = request.getTitle().trim();
    String normalizedResourcePath = request.getResourcePath().trim();
    String normalizedNotes = request.getNotes().trim();

    // update
    this.title = normalizedTitle;
    this.type = request.getType();
    this.level = request.getLevel();
    this.learningTime = request.getLearningTime();
    this.resourcePath = normalizedResourcePath;
    this.notes = normalizedNotes;
    this.isDeleted = request.getIsDeleted();
  }

}
