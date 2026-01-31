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

    @Column(name = "notice_id", nullable = false)
    private Long noticeId;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    @Column(nullable = false)
    private String url;

    public static NoticeAttachment create(
            Long noticeId,
            StoredFile file,
            String originalName
    ) {
        NoticeAttachment a = new NoticeAttachment();
        a.noticeId = noticeId;
        a.originalName = originalName;
        a.storedName = file.storedName();
        a.url = file.url();
        return a;
    }
}

