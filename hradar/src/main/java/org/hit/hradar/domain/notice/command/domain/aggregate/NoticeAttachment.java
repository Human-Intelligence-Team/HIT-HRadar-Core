package org.hit.hradar.domain.notice.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.file.StoredFile;

@Entity
@Table(name = "notice_attachment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeAttachment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @Column(name = "com_id", nullable = false)
    private Long companyId;

    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    @Column(nullable = false)
    private String url;

    @Column(name = "is_used", nullable = false)
    private boolean used;

    public static NoticeAttachment create(
            Long companyId,
            StoredFile file,
            String originalName) {
        NoticeAttachment a = new NoticeAttachment();
        a.companyId = companyId;
        a.originalName = originalName;
        a.storedName = file.storedName();
        a.url = file.url();
        a.used = false;
        return a;
    }

    public void markUsed() {
        this.used = true;
    }

    public void markUnused() {
        this.used = false;
    }

    public void attachToNotice(Long id) {
        this.noticeId = id;
    }
}
