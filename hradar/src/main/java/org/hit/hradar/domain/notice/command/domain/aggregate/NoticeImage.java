package org.hit.hradar.domain.notice.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.file.StoredFile;

@Entity
@Table(name = "notice_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "com_id", nullable = false)
    private Long companyId;

    @Column(name = "notice_id")
    private Long noticeId;

    @Column(nullable = false)
    private String url;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    /* used=false 배치로 정리 */
    @Column(name = "is_used", nullable = false)
    private boolean used;
// 삭제 추가
    public static NoticeImage create(
            Long companyId,
            StoredFile file,
            String originalName
    ) {
        NoticeImage img = new NoticeImage();
        img.companyId = companyId;
        img.url = file.url();
        img.storedName = file.storedName();
        img.originalName = originalName;
        img.used = false;
        return img;
    }

    public void markUsed() {
        this.used = true;
    }

    public void attachToNotice(Long id) {
        this.noticeId = id;
    }
}

