package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeAttachment;

import java.util.List;

public interface NoticeAttachmentRepository {
    <S extends NoticeAttachment> S save(S NoticeAttachment);

    List<NoticeAttachment> findAllByNoticeId(Long noticeId);

    List<NoticeAttachment> findAllById(Iterable<Long> ids);

    void delete(NoticeAttachment att);
}
