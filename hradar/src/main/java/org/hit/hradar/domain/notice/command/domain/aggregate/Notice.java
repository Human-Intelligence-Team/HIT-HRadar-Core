package org.hit.hradar.domain.notice.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Notice {

    @Id
    @Column(name = "notice_id")
    private Long id;

    @Column(name = "com_id", nullable = false)
    private Long companyId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NoticeStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private NoticeCategory category;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;
}
