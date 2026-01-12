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
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentsRequest;
import org.hit.hradar.domain.competencyReport.competencyReportErrorCode.CompetencyReportErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

@Entity
@Getter
@Table(name="contents")
@NoArgsConstructor
public class Contents extends BaseTimeEntity {

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

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @PrePersist
  public void prePersist() {
    if (this.isDeleted == null) {
      this.isDeleted = 'N';
    }
  }

  public Contents(String title, ContentType type, Level level, Integer learningTime, String resourcePath, String notes) {
    this.title = title;
    this.type = type;
    this.level = level;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
    this.notes = notes;
  }

  // 등록
  public static Contents create(ContentsRequest request) {

    if (request.getTitle() == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_TITLE_REQUIRED);
    }

    if(request.getType() == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_TYPE_REQUIRED);
    }

    if(request.getLevel() == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_LEVEL_REQUIRED);
    }

    String normalizedTitle = request.getTitle().trim();
    String normalizedResourcePath = request.getResourcePath().trim();
    String normalizedNotes = request.getNotes().trim();

    return new Contents(normalizedTitle
                    , request.getType()
                    , request.getLevel()
                    , request.getLearningTime()
                    , normalizedResourcePath
                    , normalizedNotes);

  }


}
