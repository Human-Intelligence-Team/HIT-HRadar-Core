package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeAttachment;


public interface NoticeAttachmentRepository {
    <S extends NoticeAttachment> S save(S NoticeAttachment);

}
