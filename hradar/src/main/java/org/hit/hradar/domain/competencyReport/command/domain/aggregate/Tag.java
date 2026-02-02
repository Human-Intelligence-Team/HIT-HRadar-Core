package org.hit.hradar.domain.competencyReport.command.domain.aggregate;


import static org.hit.hradar.domain.competencyReport.competencyReportErrorCode.CompetencyReportErrorCode.TAG_NAME_REQUIRED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

@Entity
@Table(name = "tag")
@Getter
@NoArgsConstructor
public class Tag extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tag_id")
  private Long tagId;

  @Column(name = "tag_name", nullable = false, length = 50, unique = true)
  private String tagName;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @PrePersist
  public void prePersist() {
    if (this.isDeleted == null) {
      this.isDeleted = 'N';
    }
  }

 private Tag(String tagName) {
    this.tagName = tagName;
 }

 // 등록
 public static Tag create(String tagName) {

   if (tagName == null) {
     throw new BusinessException(TAG_NAME_REQUIRED);
   }

   String normalizedTagName = tagName.trim();
   if (normalizedTagName.isEmpty()) {
     throw new BusinessException(TAG_NAME_REQUIRED);
   }

    return new Tag(normalizedTagName);
 }

}
