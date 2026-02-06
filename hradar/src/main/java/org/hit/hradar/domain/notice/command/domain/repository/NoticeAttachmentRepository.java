package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeAttachment;

import java.util.List;
import java.util.Optional;

public interface NoticeAttachmentRepository {
    <S extends NoticeAttachment> S save(S NoticeAttachment);

    List<NoticeAttachment> findAllByNoticeId(Long noticeId);

    List<NoticeAttachment> findAllById(Iterable<Long> ids);

    List<NoticeAttachment> findAllByCompanyIdAndUsedFalse(Long companyId);

    Optional<NoticeAttachment> findByStoredName(String storedName);

    void delete(NoticeAttachment att);

    List<NoticeAttachment> findAllByUsedFalseAndCreatedAtBefore(java.time.LocalDateTime dateTime);
}
