package org.hit.hradar.domain.competencyReport.command.domain.aggreage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "tag")
@Getter
@Setter
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





}
