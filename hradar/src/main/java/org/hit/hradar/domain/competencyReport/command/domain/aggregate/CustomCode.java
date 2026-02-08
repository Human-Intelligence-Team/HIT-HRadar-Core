package org.hit.hradar.domain.competencyReport.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "COMPETENCY_REPORT_CUSTOM_CODE", uniqueConstraints = {
    @UniqueConstraint(name = "uk_company_group_custom", columnNames = { "com_id", "group_code", "custom_code" })
}, indexes = {
    @Index(name = "idx_group_code", columnList = "com_id, group_code"),
    @Index(name = "idx_group_custom", columnList = "com_id, group_code, sort_order")
})
public class CustomCode extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "custom_code_id")
  private Long customCodeId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "group_code", nullable = false, length = 50)
  private String groupCode;

  @Column(name = "group_name", nullable = false, length = 50)
  private String groupName;

  @Column(name = "custom_code", length = 50)
  private String customCode;

  @Column(name = "custom_name", length = 50)
  private String customName;

  @Column(name = "custom_desc", length = 255)
  private String customDesc;

  @Column(name = "sort_order")
  private Integer sortOrder;

  @Column(name = "is_deleted", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @PrePersist
  public void prePersist() {
    if (this.isDeleted == null) {
      this.isDeleted = 'N';
    }
  }

  public CustomCode(Long comId, String groupCode, String groupName, String customCode, String customName,
      String customDesc, Character isDeleted) {
    this.comId = comId;
    this.groupCode = groupCode;
    this.groupName = groupName;
    this.customCode = customCode;
    this.customName = customName;
    this.customDesc = customDesc;
    this.isDeleted = isDeleted;

  }

  public static CustomCode create(Long comId, String groupCode, String groupName, String customCode, String customName,
      String customDesc) {
    return new CustomCode(comId, groupCode, groupName, customCode, customName, customDesc, 'N');
  }

}
