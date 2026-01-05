package org.hit.hradar.CompetencyReport.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "content_tag")
@Getter
@Setter
@NoArgsConstructor
public class ContentTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_tag_id")
  private Long contentTagId;

  @Column(name = "content_id", nullable = false)
  private Long contentId;

  @Column(name = "tag_id", nullable = false)
  private Long tagId;


}
