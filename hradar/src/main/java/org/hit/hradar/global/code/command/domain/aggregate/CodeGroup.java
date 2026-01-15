package org.hit.hradar.global.code.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "code_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeGroup extends BaseTimeEntity {
    @Id
    @Column(name = "group_code", length = 50, nullable = false)
    private String groupCode;

    @Column(name = "group_name", nullable = false, length = 100)
    private String groupName;

    @Column(name = "group_description", length = 255)
    private String description;

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    /*
    * {
          "groupCode": "GRADE_SYSTEM",
          "groupName": "등급 체계",
          "description": "회사별 평가 등급"
       }
     */
    @Builder
    private CodeGroup(
            String groupCode,
            String groupName,
            String description
    ){
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.description = description;
    }

    public void update(String groupName, String description) {
        this.groupName = groupName;
        this.description = description;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }

}
