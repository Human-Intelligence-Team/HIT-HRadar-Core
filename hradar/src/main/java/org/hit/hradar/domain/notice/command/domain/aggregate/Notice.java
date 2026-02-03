package org.hit.hradar.domain.notice.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "com_id", nullable = false)
    private Long companyId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private NoticeCategory category;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Character isDeleted;

    @PrePersist
    public void prePersist() {
        if (this.isDeleted == null) {
            this.isDeleted = 'N';
        }
    }

    public static Notice create(
            Long companyId,
            NoticeCategory category,
            String title,
            String content
    ) {
        Notice n = new Notice();
        n.companyId = companyId;
        n.category = category;
        n.title = title;
        n.content = content;
        return n;
    }

    public void update(
            NoticeCategory category,
            String title,
            String content
    ) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
