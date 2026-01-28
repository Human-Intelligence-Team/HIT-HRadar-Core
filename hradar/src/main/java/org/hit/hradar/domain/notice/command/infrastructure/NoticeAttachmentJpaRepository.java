package org.hit.hradar.domain.notice.command.infrastructure;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeAttachment;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeAttachmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeAttachmentJpaRepository extends NoticeAttachmentRepository, JpaRepository<NoticeAttachment, Long> {
}
