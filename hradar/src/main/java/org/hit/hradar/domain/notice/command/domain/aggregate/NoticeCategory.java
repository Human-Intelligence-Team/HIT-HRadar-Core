package org.hit.hradar.domain.notice.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "notice_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "com_id", nullable = false)
    private Long companyId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Character isDeleted;

    @PrePersist
    public void prePersist() {
        if (this.isDeleted == null) {
            this.isDeleted = 'N';
        }
    }

    public static NoticeCategory create(
            Long companyId,
            String name
    ) {
        NoticeCategory c = new NoticeCategory();
        c.companyId = companyId;
        c.name = name;
        return c;
    }

    public void update(String name) {
        this.name = name;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}

