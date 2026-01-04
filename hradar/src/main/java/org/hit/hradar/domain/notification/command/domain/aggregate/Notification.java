package org.hit.hradar.domain.notification.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATION")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @Column(name = "noti_id")
    private Long id;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "ref_id", nullable = false)
    private Long refId; // 관련 도메인 PK (공지, 결재, 근태 등)

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    public void markAsRead() {
        this.isRead = true;
    }
}
