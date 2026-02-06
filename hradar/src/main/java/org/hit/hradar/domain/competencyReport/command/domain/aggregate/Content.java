package org.hit.hradar.domain.competencyReport.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  //회사 id
  @Column(name = "company_id", nullable = false)
  private Long companyId;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "level")
  private String level;

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

  public Content(String title, Long companyId, String type, String level, Integer learningTime, String resourcePath, String notes) {
    this.title = title;
    this.companyId = companyId;
    this.type = type;
    this.level = level;
    this.learningTime = learningTime;
    this.resourcePath = resourcePath;
    this.notes = notes;
  }

  // 유효성
  private static void validate(String title, String type) {
    if (title == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_TITLE_REQUIRED);
    }

    if(type == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_TYPE_REQUIRED);
    }

  }

  // 등록
  public static Content create(ContentCreateRequest request) {

    // validation
    validate(request.getTitle(), request.getType());

    return new Content(request.getTitle()
                    , request.getComId()
                    , request.getType()
                    , request.getLevel()
                    , request.getLearningTime()
                    , request.getResourcePath()
                    , request.getNotes());

  }

  // 수정
  public void update(ContentUpdateRequest request) {

    // validation
    validate(request.getTitle(), request.getType());

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

  public void delete(Character isDeleted) {
    this.isDeleted = isDeleted;
  }

}
