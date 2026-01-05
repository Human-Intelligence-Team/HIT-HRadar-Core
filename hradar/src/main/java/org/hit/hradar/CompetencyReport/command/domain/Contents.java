package org.hit.hradar.CompetencyReport.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="contents")
@NoArgsConstructor
public class Contents {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_id")
  private Long contentId;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private ContentType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "level", nullable = false)
  private Level level;

  @Column(name = "estimated_learning_time")
  private String estimatedLearningTime;

  @Column(name = "link", length = 3000)
  private String link;

  @Column(name = "notes", length = 2000)
  private String notes;

  @Column(name = "use_yn", length = 1)
  private String useYn;




}
