package org.hit.hradar.domain.notice.command.infrastructure;

import org.hit.hradar.domain.notice.command.domain.aggregate.Notice;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends NoticeRepository, JpaRepository<Notice, Long> {
}
