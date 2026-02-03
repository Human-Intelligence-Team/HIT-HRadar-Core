package org.hit.hradar.domain.competencyReport.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "content_tag")
@Getter
@Setter
@NoArgsConstructor
public class ContentTag extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_tag_id")
  private Long contentTagId;

  @Column(name = "content_id", nullable = false)
  private Long contentId;

  @Column(name = "tag_id", nullable = false)
  private Long tagId;

  public ContentTag(Long contentId, Long tagId) {
    this.contentId = contentId;
    this.tagId = tagId;
  }

  public static ContentTag create(Long contentId, Long tagId) {
    return new ContentTag(contentId, tagId);
  }
}
